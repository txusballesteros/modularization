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
package com.txusballesteros.codelabs.billboard.core.view

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.txusballesteros.codelabs.billboard.core.view.lifecycle.LifecycleFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
abstract class BaseFragment : LifecycleFragment() {
    private val layoutResourceId by lazy {
        onRequestLayoutResourceId()
    }

    private var onFragmentReadyListener: (() -> Unit)? = null
    private var isReady = false

    protected val isViewReady: Boolean
        get() = isReady

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(layoutResourceId, container, false)

    @LayoutRes
    abstract fun onRequestLayoutResourceId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onSetupListeners()
        onViewReady(savedInstanceState)
        isReady = true
        onFragmentReadyListener?.invoke()
    }

    override fun onResume() {
        super.onResume()
        onViewResumed()
    }

    fun setOnFragmentReadyListener(listener: () -> Unit) {
        this.onFragmentReadyListener = listener
    }

    open fun onSetupListeners() {}

    open fun onViewReady(savedInstanceState: Bundle?) {}

    open fun onViewResumed() { }
}
