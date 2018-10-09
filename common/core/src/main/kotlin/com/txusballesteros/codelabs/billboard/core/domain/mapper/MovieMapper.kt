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
package com.txusballesteros.codelabs.billboard.core.domain.mapper

import com.txusballesteros.codelabs.billboard.api.model.MovieApiModel
import com.txusballesteros.codelabs.billboard.core.domain.model.Movie

fun MovieApiModel.map() = map(this)
fun MovieApiModel.Genre.map() = map(this)

@JvmName(name = "MovieApiModelMapper")
internal fun map(source: MovieApiModel) =
    Movie(
        id = source.id.toString(),
        originalTitle = source.originalTitle,
        originalLanguage = source.originalLanguage,
        title = source.title,
        overview = source.overview,
        releaseDate = source.releaseDate,
        hasVideo = source.hasVideo,
        backdrop = source.backdrop,
        poster = source.poster,
        voteCount = source.voteCount,
        voteAverage = source.voteAverage,
        genres = source.genres?.map { it.map() } ?: listOf()
    )

@JvmName(name = "MovieApiModelGenreMapper")
internal fun map(source: MovieApiModel.Genre) =
    Movie.Genre(
        id = source.id,
        name = source.name
    )
