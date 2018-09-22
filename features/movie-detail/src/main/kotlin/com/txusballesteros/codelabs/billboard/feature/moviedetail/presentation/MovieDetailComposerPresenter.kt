/*
 * Copyright Txus Ballesteros 2018 (@txusballesteros)
 *
 * This file is part of some open source application.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 * Contact: Txus Ballesteros <txus.ballesteros@gmail.com>
 */
package com.txusballesteros.codelabs.billboard.feature.moviedetail.presentation

import com.txusballesteros.codelabs.billboard.core.domain.model.Movie
import com.txusballesteros.codelabs.billboard.core.view.lifecycle.LifecycleView
import com.txusballesteros.codelabs.billboard.core.view.presentation.LifecyclePresenter
import com.txusballesteros.codelabs.billboard.core.domain.usecase.movie.GetMovieByIdUseCase
import com.txusballesteros.codelabs.billboard.threading.perform

class MovieDetailComposerPresenter(
    private val getMovieById: GetMovieByIdUseCase
) : LifecyclePresenter<MovieDetailComposerPresenter.View>() {

    override fun onViewAttached() {
        perform {
            view?.let { view ->
                getMovie(view.movieId)
                    .onSuccess {  movie -> view.composeView(movie) }
                    .onFailure { view.closeView() }
            }
        }
    }

    private suspend fun getMovie(id: String) = await {
        getMovieById.execute(id)
    }

    interface View : LifecycleView {
        val movieId: String

        fun composeView(movie: Movie)
        fun closeView()
    }
}
