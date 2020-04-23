package com.recipe.interfacePkg

import com.recipe.model_db.RecipeModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {
    @GET("search/")
    fun doGetRecipeList(
        @Query("q") searchText: String,
        @Query("page") page: Int
    ): Call<RecipeModel>?
}