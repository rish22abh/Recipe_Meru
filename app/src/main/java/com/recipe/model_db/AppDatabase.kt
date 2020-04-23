package com.recipe.model_db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Recipes::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun recipeDao(): RecipeDao?
}