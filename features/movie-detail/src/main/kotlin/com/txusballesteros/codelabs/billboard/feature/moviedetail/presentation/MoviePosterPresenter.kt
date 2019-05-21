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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class MoviePosterPresenter : LifecyclePresenter<MoviePosterPresenter.View>() {

    override fun onViewAttached(firstTime: Boolean) {
        view?.let { view ->
            view.movie.poster?.let { poster ->
                view.renderPoster(poster)
            } ?: view.hide()
        }
    }

    interface View : LifecycleView {
        val movie: Movie

        fun renderPoster(url: String)
        fun hide()
    }
}
