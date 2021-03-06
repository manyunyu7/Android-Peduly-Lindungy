package com.feylabs.core.data.source.remote

import android.annotation.SuppressLint
import android.util.Log
import com.feylabs.core.data.source.remote.network.ApiResponse
import com.feylabs.core.data.source.remote.network.ApiService
import com.feylabs.core.data.source.remote.response.NewsResponse
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

@SuppressLint("CheckResult")
class RemoteDataSource(private val apiService: ApiService) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(service)
            }

    }

    fun getAllNews(): Flowable<ApiResponse<List<NewsResponse.NewsResponseItem>>> {
        val resultD = PublishSubject.create<ApiResponse<List<NewsResponse.NewsResponseItem>>>()

        val client = apiService.getNewsList()

        client.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({ response ->
                val dataArray = response
                resultD.onNext(if (dataArray.isNotEmpty()) ApiResponse.Success(dataArray) else ApiResponse.Empty)
            }, { error ->
                resultD.onNext(ApiResponse.Error(error.message.toString()))
                Log.e("RemoteDataSource", error.toString())
            })


        return resultD.toFlowable((BackpressureStrategy.BUFFER))


    }

}

