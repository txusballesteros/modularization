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
package com.txusballesteros.codelabs.billboard.core.view.lifecycle

import android.os.Bundle
import android.support.v4.app.Fragment
import com.txusballesteros.codelabs.billboard.core.view.lifecycle.LifecycleView.Lifecycle
import kotlinx.coroutines.experimental.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.experimental.channels.ReceiveChannel

abstract class LifecycleFragment: Fragment(), LifecycleView {
  private val lifecycleStream = ConflatedBroadcastChannel<LifecycleView.Lifecycle>()
  private var lifecycleStatus = Lifecycle.CREATED
    set(value) {
      lifecycleStream.offer(value)
    }
  override val lifecycle: ReceiveChannel<Lifecycle>
    get() = lifecycleStream.openSubscription()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (savedInstanceState == null) {
      lifecycleStatus = Lifecycle.CREATED
    }
    lifecycleStatus = Lifecycle.INITIALIZED
  }

  override fun onResume() {
    super.onResume()
    lifecycleStatus = Lifecycle.RESUME
  }

  override fun onStart() {
    super.onStart()
    lifecycleStatus = Lifecycle.START
  }

  override fun onPause() {
    super.onPause()
    lifecycleStatus = Lifecycle.PAUSE
  }

  override fun onStop() {
    super.onStop()
    lifecycleStatus = Lifecycle.STOP
  }

  override fun onDestroy() {
    super.onDestroy()
    lifecycleStatus = Lifecycle.DESTROYED
  }
}
