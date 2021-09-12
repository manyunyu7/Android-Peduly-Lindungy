package com.feylabs.lindungipeduli.core.domain.usecase

import com.dicoding.tourismapp.core.data.Resource
import com.feylabs.lindungipeduli.core.data.source.remote.response.NewsResponse
import com.feylabs.lindungipeduli.core.domain.model.News
import io.reactivex.Flowable

interface NewsUseCase {

    fun getAllNews(): Flowable<Resource<List<News>>>

}