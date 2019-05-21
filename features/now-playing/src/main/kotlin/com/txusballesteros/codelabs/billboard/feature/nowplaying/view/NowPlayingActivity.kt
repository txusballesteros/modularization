package com.txusballesteros.codelabs.billboard.feature.nowplaying.view

import com.txusballesteros.codelabs.billboard.core.view.BaseActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class NowPlayingActivity : BaseActivity() {
    override fun onRequestFragment() = NowPlayingFragment.newInstance()
}
