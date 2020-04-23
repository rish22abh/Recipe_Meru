package com.recipe.interfacePkg

interface ApiCallBack {
    fun onRequestSuccess(reqType: Int, result: Any?)
    fun onRequestFail(reqType: Int, result: String?)
}