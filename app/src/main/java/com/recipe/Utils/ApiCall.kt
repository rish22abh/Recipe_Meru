package com.recipe.Utils

import com.recipe.interfacePkg.ApiCallBack
import com.recipe.model_db.RecipeModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiCall {
    companion object {
        fun apiRandomCall(
            reqType: Int,
            callRandomImage: Call<RecipeModel>?,
            apiCallBack: ApiCallBack
        ) {
            callRandomImage?.enqueue(object : Callback<RecipeModel> {
                override fun onFailure(call: Call<RecipeModel>, t: Throwable) {
                    apiCallBack.onRequestFail(reqType, t.message)
                }

                override fun onResponse(
                    call: Call<RecipeModel>,
                    response: Response<RecipeModel>
                ) {
                    if (response.isSuccessful)
                        apiCallBack.onRequestSuccess(reqType, response.body())
                }
            })
        }
    }
}