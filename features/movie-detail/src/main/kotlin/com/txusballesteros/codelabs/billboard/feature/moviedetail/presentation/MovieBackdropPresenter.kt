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
import com.txusballesteros.codelabs.billboard.core.domain.usecase.video.GetMovieVideosUseCase
import com.txusballesteros.codelabs.billboard.core.view.lifecycle.LifecycleView
import com.txusballesteros.codelabs.billboard.core.view.presentation.LifecyclePresenter
import com.txusballesteros.codelabs.billboard.navigation.NavigationCommand
import com.txusballesteros.codelabs.billboard.navigation.command.youtubeNavigationCommand
import com.txusballesteros.codelabs.billboard.threading.perform

class MovieBackdropPresenter(
    private val getMovieVideos: GetMovieVideosUseCase
) : LifecyclePresenter<MovieBackdropPresenter.View>() {

    override fun onViewAttached() {
        view?.let { view ->
            view.movie.backdrop?.let { backdrop ->
                view.renderBackdrop(backdrop)
            }
        }
    }

    fun onPlay() {
        perform {
            view?.let { view ->
                getVideos(view.movie.id)
                    .onSuccess { videos ->
                        videos.filter { it.site?.toLowerCase() ?: "" == "youtube" }.firstOrNull()?.let { video ->
                            video.key?.let {
                                view.navigateTo(youtubeNavigationCommand(it))
                            }
                        }
                    }
                    .onFailure {
                        view.renderVideoError()
                    }
            }
        }
    }

    private suspend fun getVideos(movieId: String) = await {
        getMovieVideos.execute(movieId)
    }

    interface View : LifecycleView {
        val movie: Movie

        fun renderBackdrop(url: String)
        fun renderVideoError()
        fun navigateTo(command: NavigationCommand)
    }
}
