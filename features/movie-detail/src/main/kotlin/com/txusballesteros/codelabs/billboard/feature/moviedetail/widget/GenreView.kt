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
package com.txusballesteros.codelabs.billboard.feature.moviedetail.widget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayout
import com.txusballesteros.codelabs.billboard.feature.moviedetail.R

class GenreView @JvmOverloads constructor(
    context: Context?,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attr, defStyleAttr) {
    companion object {
        private const val MARGIN_END_IN_DP = 8f
    }

    init {
        val parmas = FlexboxLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        parmas.setMargins(0, 0, dp2px(MARGIN_END_IN_DP).toInt(), 0)
        layoutParams = parmas
        background = resources.getDrawable(R.drawable.genre_pill, null)
        setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            resources.getDimensionPixelSize(R.dimen.text_size_s).toFloat()
        )
        setTextColor(resources.getColor(R.color.white))
    }

    fun dp2px(value: Float) =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, resources.displayMetrics)
}
