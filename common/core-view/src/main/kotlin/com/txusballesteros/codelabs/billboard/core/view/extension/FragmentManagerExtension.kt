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
package com.txusballesteros.codelabs.billboard.core.view.extension

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import org.funktionale.option.Option
import kotlin.reflect.KClass

inline fun <reified T : Fragment> FragmentManager.ifIsNotAttached(block: (FragmentManager) -> Unit) {
    if (findByTag<T>().isEmpty()) block(this)
}

inline fun <reified T : Fragment> FragmentManager.findByTag(): Option<T> {
    val tag = getTag(T::class)
    return (this.findFragmentByTag(tag) as? T)?.let { Option.Some(it) } ?: Option.None
}

inline fun <reified T : Fragment> FragmentManager.findByTagOrNull(): T? {
    val tag = getTag(T::class)
    return this.findFragmentByTag(tag) as? T
}

inline fun <reified T : Fragment> FragmentManager.findByTag(block: (T) -> Unit) {
    val tag = getTag(T::class)
    (this.findFragmentByTag(tag) as? T)?.let { block(it) }
}

fun FragmentManager.add(@IdRes placeHolder: Int, fragment: Fragment) {
    if (!fragment.isAdded) {
        val tag = getTag(fragment::class)
        beginTransaction()
            .add(placeHolder, fragment, tag)
            .commitNow()
    }
}

fun FragmentManager.attach(@IdRes placeHolder: Int, fragment: Fragment) {
    val tag = getTag(fragment::class)
    beginTransaction()
        .replace(placeHolder, fragment, tag)
        .commitNowAllowingStateLoss()
}


fun FragmentManager.remove(fragment: Fragment) {
    beginTransaction()
        .remove(fragment)
        .commitNow()
}

fun getTag(type: KClass<*>) = type.java.name
