package com.pass.taskmanager.utils

import android.content.Context
import android.provider.Settings.Global.getString
import com.google.android.gms.auth.api.identity.BeginSignInRequest

import com.pass.taskmanager.R


//fun getGoogleSignInClient(context: Context):  {
//    val signInOptions =BeginSignInRequest.builder()
//        .setGoogleIdTokenRequestOptions(
//            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//            .setSupported(true)
//            // Your server's client ID, not your Android client ID.
//            .setServerClientId(context.getString(R.string.default_web_client_id))
//            // Only show accounts previously used to sign in.
//            .setFilterByAuthorizedAccounts(true)
//            .build())
//        .build();
//
//
//
//}