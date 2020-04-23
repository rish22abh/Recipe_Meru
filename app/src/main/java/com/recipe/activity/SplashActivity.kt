package com.recipe.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.recipe.*
import com.recipe.Utils.SharedPrefUtils

class SplashActivity : BaseActivity() {
    private var mHandler: Handler? = null
    private var mRunnable: Runnable = Runnable {
        val intent: Intent = if (SharedPrefUtils.getString(
                this,
                SharedPrefUtils.LOGIN_MAIL,
                null
            ) != null)
            Intent(this, MainActivity::class.java)
        else
            Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)

        mHandler = Handler()
    }

    override fun onResume() {
        super.onResume()
        mHandler?.postDelayed(mRunnable, 3000)
    }

    override fun onPause() {
        super.onPause()
        mHandler?.removeCallbacks(mRunnable)
    }
}