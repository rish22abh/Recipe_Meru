package com.recipe.asyncPkg

import android.app.Application
import android.os.AsyncTask
import android.os.Handler
import android.os.Message
import com.recipe.model_db.DatabaseClient
import com.recipe.model_db.Recipes


class GetAllListAsync(
    private val application: Application,
    private var handler: Handler) : AsyncTask<Void, Void, List<Recipes>>() {
    override fun doInBackground(vararg params: Void?): List<Recipes>? {
        return DatabaseClient.getInstance(application)?.getAppDatabase()?.recipeDao()?.getAll()
    }

    override fun onPostExecute(result: List<Recipes>?) {
        super.onPostExecute(result)
        val msg = Message.obtain(handler, 0, result)
        handler.sendMessage(msg)
    }

}