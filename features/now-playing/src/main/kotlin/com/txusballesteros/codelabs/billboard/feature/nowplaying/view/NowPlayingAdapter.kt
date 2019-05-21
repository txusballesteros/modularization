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
package com.txusballesteros.codelabs.billboard.feature.nowplaying.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.txusballesteros.codelabs.billboard.core.domain.model.Movie
import com.txusballesteros.codelabs.billboard.core.view.extension.download
import com.txusballesteros.codelabs.billboard.feature.nowplaying.R
import kotlinx.android.synthetic.main.item_now_playing.view.*

class NowPlayingAdapter(
    private val onItemTap: (View, Movie) -> Unit
) : RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>() {

    private val cache = mutableListOf<Movie>()

    fun addAll(movies: List<Movie>) {
        cache.addAll(movies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                        .from(parent.context).inflate(R.layout.item_now_playing, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = cache.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder.itemView) {
        val movie = cache[position]
        posterView.download(movie.poster)
        mainView.setOnClickListener { onItemTap(posterView, movie) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
