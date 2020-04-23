package com.recipe.google_login

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class GPlusManager private constructor() {

    companion object {
        private var mGPlusManager: GPlusManager? = null
        fun getInstance(): GPlusManager {
            if (mGPlusManager == null)
                mGPlusManager =
                    GPlusManager()

            return mGPlusManager!!
        }
    }

    private var mListener: GoogleLoginListener? = null
    private var googleSignInClient: GoogleSignInClient? = null
    private var account: GoogleSignInAccount? = null
    private var mContext: Context? = null

    fun gPlusLogin(
        context: Context
    ) {
        if (context !is GoogleLoginListener)
            return
        this.mListener = context
        this.mContext = context
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        if (!isContextDestroyed(mContext)) {
            googleSignInClient = GoogleSignIn.getClient(mContext!!, gso)
            val signInIntent: Intent = googleSignInClient!!.signInIntent
            (mContext as Activity).startActivityForResult(signInIntent, 1)
        } else {
            onFailure("The request was cancelled or some error Occurred")
        }
    }

    fun onActivityResult(data: Intent) {
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            account = task.getResult(ApiException::class.java)
            mListener!!.onGoogleLoginSuccess(account)
            googleSignInClient!!.signOut()
        } catch (e: Exception) {
            onFailure(e.message)
            googleSignInClient!!.signOut()
        }
    }

    private fun onFailure(error: String?) {
        account = null
        mListener!!.onGoogleLoginFailure(error)
    }

    private fun isContextDestroyed(context: Context?): Boolean {
        return context is Activity && context.isFinishing
    }

    interface GoogleLoginListener {
        fun onGoogleLoginSuccess(account: GoogleSignInAccount?)
        fun onGoogleLoginFailure(error: String?)
    }

}