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
package com.txusballesteros.codelabs.billboard

import android.app.Application
import com.txusballesteros.codelabs.billboard.threading.APPLICATION_BG
import com.txusballesteros.codelabs.billboard.threading.APPLICATION_MAIN
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newFixedThreadPoolContext

class Application : Application() {
    @ObsoleteCoroutinesApi
    override fun onCreate() {
        super.onCreate()
        initializeThreading()
    }

    @ObsoleteCoroutinesApi
    private fun initializeThreading() {
        APPLICATION_MAIN = Dispatchers.Main + CoroutineExceptionHandler { _, error ->
            throw error
        }
        APPLICATION_BG = newFixedThreadPoolContext(
            2 * Runtime.getRuntime().availableProcessors(),
            "bg"
        ) + CoroutineExceptionHandler { _, error ->
            throw error
        }
    }
}
