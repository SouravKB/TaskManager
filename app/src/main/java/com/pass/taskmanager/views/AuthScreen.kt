package com.pass.taskmanager.views

import android.app.Activity.RESULT_OK
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider.getCredential
import com.google.firebase.firestore.FirebaseFirestore
import com.pass.taskmanager.R
import com.pass.taskmanager.misc.AuthRepository
import com.pass.taskmanager.models.Response.*
import com.pass.taskmanager.viewmodels.AuthViewModel

private const val TAG = "AuthScreen"

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(LocalContext.current),
    navigateToProfileScreen: () -> Unit
) {
    Scaffold(
        topBar = {
            AuthTopBar()
        },
        content = { padding ->
            AuthContent(padding, viewModel)
        }
    )

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                try {
                    Log.d(TAG, "AuthScreen: result")
                    val credentials =
                        viewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
                    val googleIdToken = credentials.googleIdToken
                    val googleCredentials = getCredential(googleIdToken, null)
                    viewModel.signInWithGoogle(googleCredentials)
                } catch (it: ApiException) {
                    print(it)
                }
            }
        }

    fun launch(signInResult: BeginSignInResult) {
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent)
    }

    when (val oneTapSignInResponse = viewModel.oneTapSignInState.value) {
        is Loading -> ProgressBar()
        is Success -> {
            oneTapSignInResponse.data?.let {
                LaunchedEffect(it) {
                    launch(it)
                }
            }
        }
        is Failure -> {
            oneTapSignInResponse.e?.let {
                LaunchedEffect(Unit) {
                    print(it)
                    if (it.message == "SIGN_IN_ERROR_MESSAGE") {
                        viewModel.oneTapSignUp()
                    }
                }
            }
        }
    }

    when (val oneTapSignUpResponse = viewModel.oneTapSignUpState.value) {
        is Loading -> ProgressBar()
        is Success -> {
            oneTapSignUpResponse.data?.let {
                LaunchedEffect(it) {
                    launch(it)
                }
            }
        }
        is Failure -> oneTapSignUpResponse.e?.let {
            LaunchedEffect(Unit) {
                print(it)
            }
        }
    }

    when (val signInResponse = viewModel.signInState.value) {
        is Loading -> ProgressBar()
        is Success -> {
            signInResponse.data?.let { isNewUser ->
                if (isNewUser) {
                    LaunchedEffect(isNewUser) {
                        viewModel.createUser()
                    }
                } else {
                    navigateToProfileScreen()
                }
            }
        }
        is Failure -> signInResponse.e?.let {
            LaunchedEffect(Unit) {
                print(it)
            }
        }
    }

    when (val createUserResponse = viewModel.createUserState.value) {
        is Loading -> ProgressBar()
        is Success -> {
            createUserResponse.data?.let { isUserCreated ->
                if (isUserCreated) {
                    navigateToProfileScreen()
                }
            }
        }
        is Failure -> createUserResponse.e?.let {
            LaunchedEffect(Unit) {
                print(it)
            }
        }
    }
}

@Composable
fun AuthTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "AUTH SCREEN"
            )
        }
    )
}

@Composable
fun ProgressBar() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun AuthContent(
    padding: PaddingValues,
    viewModel: AuthViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = BottomCenter
    ) {
        SignInButton {
            Log.d(TAG, "AuthContent: ")
            viewModel.oneTapSignUp()
        }
    }
}

@Composable
fun SignInButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(bottom = 48.dp),
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(R.color.purple_700)
        )
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.ic_launcher_foreground
            ),
            contentDescription = ""
        )
        Text(
            text = "SIGN_IN_WITH_GOOGLE",
            modifier = Modifier.padding(6.dp),
            fontSize = 18.sp
        )
    }
}

fun hiltViewModel(context: Context): AuthViewModel {
    return AuthViewModel(
        AuthRepository(
            FirebaseAuth.getInstance(),
            Identity.getSignInClient(context),
            BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(context.getString(R.string.web_client_id))
                        .setFilterByAuthorizedAccounts(true)
                        .build()
                )
                .setAutoSelectEnabled(true)
                .build(),
            BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(context.getString(R.string.web_client_id))
                        .setFilterByAuthorizedAccounts(false)
                        .build()
                )
                .setAutoSelectEnabled(true)
                .build(),
            GoogleSignIn.getClient(
                context,
                GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context.getString(R.string.web_client_id))
                    .requestEmail()
                    .build()
            ),
            FirebaseFirestore.getInstance().collection("users")
        ),
        Identity.getSignInClient(context)
    )
}
