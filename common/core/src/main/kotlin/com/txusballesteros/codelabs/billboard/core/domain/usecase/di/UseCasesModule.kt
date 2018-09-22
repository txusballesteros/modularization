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
package com.txusballesteros.codelabs.billboard.core.domain.usecase.di

import com.txusballesteros.codelabs.billboard.core.domain.usecase.movie.GetMovieByIdUseCase
import com.txusballesteros.codelabs.billboard.core.domain.usecase.video.GetMovieVideosUseCase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

internal val useCasesModule = Kodein.Module(name = "CoreUseCasesModule") {
    bind<GetMovieVideosUseCase>() with provider { GetMovieVideosUseCase(instance()) }
    bind<GetMovieByIdUseCase>() with provider { GetMovieByIdUseCase(instance()) }
}
