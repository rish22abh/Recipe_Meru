package com.recipe.model_db

import android.content.Context
import androidx.room.Room

class DatabaseClient {
    private var mCtx: Context? = null

    //our app database object
    private var appDatabase: AppDatabase? = null

    private constructor(mCtx: Context){
        this.mCtx = mCtx
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase::class.java, "data").build()
    }

    companion object {
        private var mInstance: DatabaseClient? = null
        @Synchronized
        fun getInstance(mCtx: Context): DatabaseClient? {
            if (mInstance == null) {
                mInstance =
                    DatabaseClient(mCtx)
            }
            return mInstance
        }
    }

    fun getAppDatabase(): AppDatabase? {
        return appDatabase
    }
}