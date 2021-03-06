package com.feylabs.core.data.source.local

import android.content.Context
import com.feylabs.core.data.source.local.entity.NewsEntity
import com.feylabs.core.data.source.local.room.MyRoomDatabase
import com.feylabs.core.data.source.local.room.NewsDao
import io.reactivex.Flowable

class LocalDataSource(
    private val context: Context,
    private val myRoomDatabase: MyRoomDatabase
) {


    fun getAllNews(): Flowable<List<NewsEntity>> = myRoomDatabase.newsDao().getAllNews()

    fun checkIfFavorite(id: String): Flowable<Boolean> =
        myRoomDatabase.newsDao().checkIfFavorite(id)

    fun getFavoriteNews(): Flowable<List<NewsEntity>> = myRoomDatabase.newsDao().getFavoriteNews()

    fun insertNews(newsList: List<NewsEntity>) = myRoomDatabase.newsDao().insertNews(newsList)

    fun setFavoriteNews(tourism: NewsEntity, newState: Boolean) {
        tourism.is_favorite = newState
        myRoomDatabase.newsDao().updateFavoriteTourism(tourism)
    }

}