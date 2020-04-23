package com.recipe.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.recipe.R
import com.recipe.Utils.SharedPrefUtils
import com.recipe.Utils.Utils
import com.recipe.google_login.GPlusManager
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener,
    GPlusManager.GoogleLoginListener {

    private var mGPlusManager: GPlusManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_glogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_glogin -> {
                if (Utils.isNetworkAvailable(this)) {
                    mGPlusManager = GPlusManager.getInstance()
                    mGPlusManager?.gPlusLogin(this)
                } else {
                    Toast.makeText(this, "No Internet", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && data != null)
            mGPlusManager?.onActivityResult(data)
    }

    override fun onGoogleLoginSuccess(account: GoogleSignInAccount?) {
        if (account != null)
            initiateGoogleLoginFlow(account)
        else onGoogleLoginFailure("Some Error occured")
    }

    private fun initiateGoogleLoginFlow(account: GoogleSignInAccount) {
        if (account.email != null && account.email!!.isNotEmpty()) {
            SharedPrefUtils.putString(
                this,
                SharedPrefUtils.LOGIN_MAIL,
                account.email
            )
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onGoogleLoginFailure(error: String?) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }
}
