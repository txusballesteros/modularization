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
import android.view.View.GONE
import com.txusballesteros.codelabs.billboard.core.domain.model.Movie
import com.txusballesteros.codelabs.billboard.core.view.BaseFragment
import com.txusballesteros.codelabs.billboard.core.view.extension.download
import com.txusballesteros.codelabs.billboard.core.view.extension.withArguments
import com.txusballesteros.codelabs.billboard.feature.moviedetail.R
import com.txusballesteros.codelabs.billboard.feature.moviedetail.di.featureComponent
import com.txusballesteros.codelabs.billboard.feature.moviedetail.presentation.MovieBackdropPresenter
import com.txusballesteros.codelabs.billboard.feature.moviedetail.presentation.MoviePosterPresenter
import kotlinx.android.synthetic.main.fragment_movie_backdrop.*
import kotlinx.android.synthetic.main.fragment_movie_poster.*
import org.kodein.di.generic.instance
import java.lang.IllegalArgumentException

class MoviePosterFragment : BaseFragment(), MoviePosterPresenter.View {
    companion object {
        private const val ARGUMENT_MOVIE = "argument:movie"

        fun newInstance(movie: Movie) = MoviePosterFragment().withArguments(
            ARGUMENT_MOVIE to movie
        )
    }

    override val movie: Movie
        get() = arguments?.getSerializable(ARGUMENT_MOVIE) as? Movie ?: throw IllegalArgumentException("The Movie argument can not be null.")

    private val presenter: MoviePosterPresenter by featureComponent.instance()

    override fun onRequestLayoutResourceId() = R.layout.fragment_movie_poster

    override fun onViewReady(savedInstanceState: Bundle?) {
        presenter.onViewReady(this)
    }

    override fun renderPoster(url: String) {
        poster.download(url) {
            activity?.supportStartPostponedEnterTransition()
        }
    }

    override fun hide() {
        poster.visibility = GONE
    }
}
