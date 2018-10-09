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
import com.txusballesteros.codelabs.billboard.exceptions.ViewDestroyedException
import com.txusballesteros.codelabs.billboard.threading.APPLICATION_BG
import com.txusballesteros.codelabs.billboard.threading.perform
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.consumeEach

abstract class LifecyclePresenter<T : LifecycleView> {
    protected var view: T? = null
    private val disposable = Job()

    private val lifecycle: ReceiveChannel<LifecycleView.Lifecycle>?
        get() = view?.lifecycle

    fun onViewReady(view: T) {
        this.view = view
        subscribeToViewLifecycle()
        onViewAttached()
    }

    protected fun onViewDestroyed() {}

    private fun subscribeToViewLifecycle() {
        async(context = APPLICATION_BG, parent = disposable) {
            lifecycle?.consumeEach {
                if (it == LifecycleView.Lifecycle.DESTROYED) {
                    view = null
                    disposable.cancel(cause = ViewDestroyedException())
                    perform { onViewDestroyed() }
                }
            }
        }.invokeOnCompletion {
            lifecycle?.cancel(cause = ViewDestroyedException())
        }
    }

    protected open fun onViewAttached() {}

    protected fun <T> bg(execution: suspend CoroutineScope.() -> T) : Deferred<T> {
        val process = async(context = APPLICATION_BG, parent = disposable) { execution() }
        process.invokeOnCompletion { error -> error?.let { perform { throw it } } }
        return process
    }


    protected suspend fun <T> await(block: suspend () -> T): T = bg { block() }.await()
}
