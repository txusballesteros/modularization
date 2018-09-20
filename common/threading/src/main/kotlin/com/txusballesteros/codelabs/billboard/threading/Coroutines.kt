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
package com.txusballesteros.codelabs.billboard.threading

import com.txusballesteros.codelabs.billboard.exceptions.ViewDestroyedException
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.coroutines.experimental.suspendCoroutine

lateinit var APPLICATION_MAIN: CoroutineContext
lateinit var APPLICATION_BG: CoroutineContext

fun perform(block: suspend CoroutineScope.() -> Unit) =
        launch(APPLICATION_MAIN + CoroutineExceptionHandler { _, throwable ->
            if (throwable is JobCancellationException) {
                if (throwable.cause !is ViewDestroyedException) throw throwable
            } else {
                throw throwable
            }
        }) { block() }

fun <T> bg(block: suspend CoroutineScope.() -> T): Deferred<T> {
    val process= async(context = APPLICATION_BG) { block() }
    process.invokeOnCompletion { error -> error?.let { perform { throw it } } }
    return process
}


suspend fun <T> waitUntil(block: (Continuation<T>) -> Unit) = suspendCoroutine<T> { block(it) }

fun subscribe(block: suspend () -> Unit) = perform {
    bg {
        block()
    }
}

fun <T> ReceiveChannel<T>.subscribe(block: (T) -> Unit) {
    perform {
        bg { consumeEach { value -> block(value) } }.invokeOnCompletion { e -> e?.let { throw e } }
    }
}
