package com.recipe.model_db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipeModel(var count: Int = 0,var recipes:List<Recipes>): Parcelable

@Parcelize
@Entity
data class Recipes(@ColumnInfo(name = "image_url")var image_url: String? = null,
                   @ColumnInfo(name = "social_rank")var social_rank: Double = 0.0,
                   @ColumnInfo(name = "_id") var _id: String? = null,
                   @ColumnInfo(name = "publisher")var publisher: String? = null,
                   @ColumnInfo(name = "source_url")var source_url: String? = null,
                   @ColumnInfo(name = "recipe_id")var recipe_id: String? = null,
                   @ColumnInfo(name = "publisher_url")var publisher_url: String? = null,
                   @ColumnInfo(name = "title")var title: String? = null,
                   @ColumnInfo(name = "searchItem")var searchItem: String? = null,
                   @PrimaryKey(autoGenerate = true)var primaryKey: Int): Parcelable