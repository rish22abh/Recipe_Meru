package com.recipe.asyncPkg

import android.app.Application
import android.os.AsyncTask
import android.os.Handler
import android.os.Message
import com.recipe.model_db.DatabaseClient

class GetSearchAsync(
    private val application: Application,
    private var handler: Handler
) : AsyncTask<Void, Void, String>() {
    override fun doInBackground(vararg params: Void?): String? {
        return DatabaseClient.getInstance(application)?.getAppDatabase()?.recipeDao()
            ?.getSearchedItem()
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        if (result != null) {
            val msg = Message.obtain(handler, 2, result)
            handler.sendMessage(msg)
        }
    }

}