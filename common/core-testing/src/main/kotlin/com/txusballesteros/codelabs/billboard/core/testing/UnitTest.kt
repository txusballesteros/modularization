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
package com.txusballesteros.codelabs.billboard.core.testing

import com.nhaarman.mockito_kotlin.reset
import com.txusballesteros.codelabs.billboard.core.testing.threading.CoroutineContextForTest
import com.txusballesteros.codelabs.billboard.threading.APPLICATION_BG
import com.txusballesteros.codelabs.billboard.threading.APPLICATION_MAIN
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance

@Suppress("IllegalIdentifier")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class UnitTest {
    val mocksCache = mutableListOf<Any>()

    @BeforeEach
    fun onBefore() {
        APPLICATION_MAIN = CoroutineContextForTest
        APPLICATION_BG = CoroutineContextForTest
        reset(*mocksCache.toTypedArray())
        onPrepareTest()
    }

    @AfterAll
    fun onAfterAll() {
        mocksCache.clear()
    }

    inline fun <reified T : Any> mock(): T {
        val mockObject = com.nhaarman.mockito_kotlin.mock<T>()
        mocksCache.add(mockObject)
        return mockObject
    }

    open fun onPrepareTest() {}
}
