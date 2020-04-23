package com.recipe.asyncPkg

import android.app.Application
import android.os.AsyncTask
import com.recipe.model_db.DatabaseClient
import com.recipe.model_db.Recipes

class DeleteItemAsync(
    private val application: Application
) : AsyncTask<Recipes, Void, Void>() {
    override fun doInBackground(vararg params: Recipes): Void? {
        DatabaseClient.getInstance(application)?.getAppDatabase()?.recipeDao()?.delete(params[0])
        return null
    }
}