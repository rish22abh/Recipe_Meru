package com.recipe.asyncPkg

import android.app.Application
import android.os.AsyncTask
import com.recipe.model_db.DatabaseClient
import com.recipe.model_db.RecipeModel

class UpdateDbAsync(
    private val application: Application,
    private val deleteAll: Boolean,
    private val searchTxt: String
) : AsyncTask<RecipeModel, Void, Void>() {

    override fun doInBackground(vararg params: RecipeModel): Void? {
        if (params.isNotEmpty()) {
            if (deleteAll) {
                DatabaseClient.getInstance(application)?.getAppDatabase()?.recipeDao()?.deleteAll()
            }
            for (i in params[0].recipes.indices) {
                params[0].recipes[i].searchItem = searchTxt
                DatabaseClient.getInstance(application)?.getAppDatabase()?.recipeDao()
                    ?.insert(params[0].recipes[i])
            }
        }
        return null
    }
}