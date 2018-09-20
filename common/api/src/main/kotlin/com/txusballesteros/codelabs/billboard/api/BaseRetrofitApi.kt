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
package com.txusballesteros.codelabs.billboard.api

import com.txusballesteros.codelabs.billboard.exceptions.NetworkException
import retrofit2.Call
import java.io.IOException

abstract class BaseRetrofitApi {
    inline fun <T> execute(f: () -> Call<T>): T {
        val body: T
        try {
            body = f().execute().body()!!
        } catch (error: IOException) {
            throw NetworkException()
        }
        return body
    }

    inline fun <T> executeVoid(f: () -> Call<T>) {
        try {
            f().execute()
        } catch (error: IOException) {
            throw NetworkException()
        }
    }
}
