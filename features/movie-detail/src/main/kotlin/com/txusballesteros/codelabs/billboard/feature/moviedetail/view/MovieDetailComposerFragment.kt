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
package com.txusballesteros.codelabs.billboard.feature.moviedetail.view

import android.os.Bundle
import com.txusballesteros.codelabs.billboard.core.domain.model.Movie
import com.txusballesteros.codelabs.billboard.core.view.BaseFragment
import com.txusballesteros.codelabs.billboard.core.view.extension.attach
import com.txusballesteros.codelabs.billboard.core.view.extension.withArguments
import com.txusballesteros.codelabs.billboard.feature.moviedetail.R
import com.txusballesteros.codelabs.billboard.feature.moviedetail.di.featureComponent
import com.txusballesteros.codelabs.billboard.feature.moviedetail.presentation.MovieDetailComposerPresenter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.kodein.di.generic.instance

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class MovieDetailComposerFragment : BaseFragment(), MovieDetailComposerPresenter.View {
    companion object {
        private const val ARGUMENT_ID = "argument:id"

        fun newInstance(id: String) = MovieDetailComposerFragment().withArguments(
            ARGUMENT_ID to id
        )
    }

    override val movieId: String
        get() = arguments?.getString(ARGUMENT_ID) ?: throw IllegalArgumentException("The ID parameter can not be null.")

    private val presenter : MovieDetailComposerPresenter by featureComponent.instance()

    override fun onRequestLayoutResourceId() = R.layout.fragment_movie_detail_composer

    override fun onViewReady(savedInstanceState: Bundle?) {
        presenter.onViewReady(this)
    }

    override fun composeView(movie: Movie) {
        attach(R.id.backdrop_holder, MovieBackdropFragment.newInstance(movie))
        attach(R.id.poster_holder, MoviePosterFragment.newInstance(movie))
        attach(R.id.rating_holder, MovieRatingFragment.newInstance(movie))
        attach(R.id.title_holder, MovieTitleFragment.newInstance(movie))
        attach(R.id.overview_holder, MovieOverviewFragment.newInstance(movie))
        attach(R.id.genres_holder, MovieGenresFragment.newInstance(movie))
    }

    override fun closeView() {
        activity?.finish()
    }
}
