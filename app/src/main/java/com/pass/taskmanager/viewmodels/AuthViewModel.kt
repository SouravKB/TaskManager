package com.pass.taskmanager.viewmodels

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.pass.taskmanager.misc.AuthRepository
import com.pass.taskmanager.utils.Response
import com.pass.taskmanager.utils.Response.Success
import com.pass.taskmanager.views.hiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repo: AuthRepository,
    val oneTapClient: SignInClient
) : ViewModel() {

    val isUserAuthenticated get() = repo.isUserAuthenticatedInFirebase()

    private val _oneTapSignInState = mutableStateOf<Response<BeginSignInResult>>(
        Success(null)
    )
    val oneTapSignInState: State<Response<BeginSignInResult>> = _oneTapSignInState

    private val _oneTapSignUpState = mutableStateOf<Response<BeginSignInResult>>(Success(null))
    val oneTapSignUpState: State<Response<BeginSignInResult>> = _oneTapSignUpState

    private val _signInState = mutableStateOf<Response<Boolean>>(Success(null))
    val signInState: State<Response<Boolean>> = _signInState

    private val _createUserState = mutableStateOf<Response<Boolean>>(Success(null))
    val createUserState: State<Response<Boolean>> = _createUserState

    fun getAuthState() = liveData(Dispatchers.IO) {
        repo.getFirebaseAuthState().collect { response ->
            emit(response)
        }
    }

    fun oneTapSignIn() {
        viewModelScope.launch {
            repo.oneTapSignInWithGoogle().collect { response ->
                _oneTapSignInState.value = response
            }
        }
    }

    fun oneTapSignUp() {
        viewModelScope.launch {
            repo.oneTapSignUpWithGoogle().collect { response ->
                _oneTapSignUpState.value = response
            }
        }
    }

    fun signInWithGoogle(googleCredential: AuthCredential) {
        viewModelScope.launch {
            repo.firebaseSignInWithGoogle(googleCredential).collect { response ->
                _signInState.value = response
            }
        }
    }

    fun createUser() {
        viewModelScope.launch {
            repo.createUserInFirestore().collect { response ->
                _createUserState.value = response
            }
        }
    }

    class AuthVMFactory(
        private val context: Context
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return hiltViewModel(context) as T
        }
    }
}
