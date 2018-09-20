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
package com.txusballesteros.codelabs.billboard.api.instrumentation

import com.txusballesteros.codelabs.billboard.exceptions.*
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection

class ResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (!response.isSuccessful) {
            when (response.code()) {
                HttpURLConnection.HTTP_NOT_FOUND -> throwError(NotFoundException())
                HttpURLConnection.HTTP_FORBIDDEN -> throwError(ForbiddenException())
                HttpURLConnection.HTTP_INTERNAL_ERROR -> throwError(InternalErrorException())
                HttpURLConnection.HTTP_UNAUTHORIZED -> throwError(UnauthorizedException())
                else -> throwError(NetworkException())
            }
        }
        return response
    }

    private fun throwError(error: NetworkException) {
        throw error
    }
}
