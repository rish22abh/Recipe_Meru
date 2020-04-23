package com.recipe.model_db

import androidx.room.*


@Dao
interface RecipeDao {
    @Query("SELECT * FROM Recipes")
    fun getAll(): List<Recipes>?

    @Insert
    fun insert(task: Recipes)

    @Delete
    fun delete(task: Recipes)

    @Update
    fun update(task: Recipes?)

    @Query("DELETE FROM Recipes")
    fun deleteAll()

    @Query("SELECT searchItem FROM Recipes WHERE primaryKey=1")
    fun getSearchedItem(): String
}