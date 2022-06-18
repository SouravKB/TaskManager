package com.pass.taskmanager.misc

import android.util.Log
import androidx.compose.runtime.ExperimentalComposeApi
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.pass.taskmanager.models.Response
import com.pass.taskmanager.models.Response.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient,
    private var signInRequest: BeginSignInRequest,
    private var signUpRequest: BeginSignInRequest,
    private var signInClient: GoogleSignInClient,
    private val usersRef: CollectionReference,
) {
    private val TAG = "AuthRepository"

    fun isUserAuthenticatedInFirebase() = auth.currentUser != null

    suspend fun oneTapSignInWithGoogle() = flow {
        try {
            emit(Loading)
            Log.d(TAG, "oneTapSignInWithGoogle: ")
            val result = oneTapClient.beginSignIn(signInRequest).await()
            Log.d(TAG, "oneTapSignInWithGoogle: end")
            emit(Success(result))
        } catch (e: Exception) {
            Log.d(TAG, "oneTapSignInWithGoogle: catch ${e.message}")
            emit(Failure(e))
        }
    }

    suspend fun oneTapSignUpWithGoogle() = flow {
        try {
            emit(Loading)
            val result = oneTapClient.beginSignIn(signUpRequest).await()
            emit(Success(result))
        } catch (e: Exception) {
            emit(Failure(e))
        }
    }

    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential) = flow {
        try {
            emit(Loading)
            val authResult = auth.signInWithCredential(googleCredential).await()
            val isNewUser = authResult.additionalUserInfo?.isNewUser
            emit(Success(isNewUser))
        } catch (e: Exception) {
            emit(Failure(e))
        }
    }

    suspend fun createUserInFirestore() = flow {
        try {
            emit(Loading)
//            auth.currentUser?.apply {
//                usersRef.document(uid).set(mapOf(
//                    DISPLAY_NAME to displayName,
//                    EMAIL to email,
//                    PHOTO_URL to photoUrl?.toString(),
//                    CREATED_AT to serverTimestamp()
//                )).await()
//                emit(Success(true))
//            }
        } catch (e: Exception) {
            emit(Failure(e))
        }
    }

    fun getFirebaseAuthState() = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    suspend fun signOut() = flow {
        try {
            emit(Loading)
            auth.signOut()
            oneTapClient.signOut().await()
            emit(Success(true))
        } catch (e: Exception) {
            emit(Failure(e))
        }
    }

    suspend fun revokeAccess() = flow {
        try {
            emit(Loading)
            auth.currentUser?.apply {
                usersRef.document(uid).delete().await()
                delete().await()
                signInClient.revokeAccess().await()
                oneTapClient.signOut().await()
            }
            emit(Success(true))
        } catch (e: Exception) {
            emit(Failure(e))
        }
    }

    fun getDisplayName() = auth.currentUser?.displayName.toString()

    fun getPhotoUrl() = auth.currentUser?.photoUrl.toString()
}