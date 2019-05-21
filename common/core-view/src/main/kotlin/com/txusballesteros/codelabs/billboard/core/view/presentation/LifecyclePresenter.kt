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
package com.txusballesteros.codelabs.billboard.core.view.presentation

import com.txusballesteros.codelabs.billboard.core.view.lifecycle.LifecycleView
import com.txusballesteros.codelabs.billboard.threading.APPLICATION_BG
import com.txusballesteros.codelabs.billboard.threading.finish
import com.txusballesteros.codelabs.billboard.threading.perform
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
abstract class LifecyclePresenter<T : LifecycleView> : CoroutineScope {
    protected var view: T? = null
    private val disposable = Job()

    private val lifecycle: ReceiveChannel<LifecycleView.Lifecycle>?
        get() = view?.lifecycle

    override val coroutineContext: CoroutineContext
        get() = APPLICATION_BG + disposable

    @ObsoleteCoroutinesApi
    fun onViewReady(view: T, firstTime: Boolean = false) {
        this.view = view
        subscribeToViewLifecycle()
        onViewAttached(firstTime)
    }

    @ObsoleteCoroutinesApi
    private fun subscribeToViewLifecycle() {
        async {
            lifecycle?.consumeEach {
                if (it == LifecycleView.Lifecycle.DESTROYED) {
                    onViewDestroyed()
                    view = null
                    disposable.finish()
                }
            }
        }.invokeOnCompletion {
            lifecycle?.finish()
        }
    }

    protected open fun onViewAttached(firstTime: Boolean = false) { }

    protected open fun onViewDestroyed() { }

    @Suppress("DeferredIsResult")
    private fun <T> bg(execution: suspend CoroutineScope.() -> T) : Deferred<T> {
        val process = async { execution() }
        process.invokeOnCompletion { error -> error?.let { perform { throw it } } }
        return process
    }

    protected suspend fun <T> await(block: suspend () -> T): T = bg { block() }.await()
}
