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
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.txusballesteros.codelabs.billboard.core.view.extension.attach
import com.txusballesteros.codelabs.billboard.core.view.callback.OnBackPressed

abstract class BaseActivity : AppCompatActivity() {
    private val fragmentsCount: Int
        get() = supportFragmentManager.fragments?.size ?: 0

    private val hasFragments: Boolean
        get() = (fragmentsCount > 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.attach(android.R.id.content, onRequestFragment())
        }
    }

    abstract fun onRequestFragment(): Fragment

    override fun onBackPressed() {
        if (hasFragments) {
            var attended = false
            supportFragmentManager.fragments?.forEach { childFragment ->
                if (childFragment is OnBackPressed) {
                    attended = childFragment.onBackPressed()
                }
            }
            if (!attended) super.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }
}
