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
package com.txusballesteros.codelabs.billboard.feature.nowplaying.presentation

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.txusballesteros.codelabs.billboard.core.testing.UnitTest
import com.txusballesteros.codelabs.billboard.feature.nowplaying.domain.NowPlayingRepository
import org.funktionale.tries.Try
import org.junit.jupiter.api.Test

internal class NowPlayingPresenterUnitTest : UnitTest() {
    private lateinit var presenter: NowPlayingPresenter
    private val viewMock: NowPlayingPresenter.View = mock()
    private val repositoryMock: NowPlayingRepository = mock()

    override fun onPrepareTest() {
        presenter = NowPlayingPresenter(repositoryMock)
    }

    @Test
    fun `render now playing movies list`() {
        whenever(repositoryMock.getMovies()).thenReturn(Try.Success(listOf()))

        presenter.onViewReady(viewMock)

        verify(viewMock).renderMovies(any())
    }

    @Test
    fun `render error`() {
        whenever(repositoryMock.getMovies()).thenReturn(Try.Failure(IllegalArgumentException()))

        presenter.onViewReady(viewMock)

        verify(viewMock).renderError()
    }
}
