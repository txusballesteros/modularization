package com.txusballesteros.codelabs.billboard.feature.nowplaying.view

import com.txusballesteros.codelabs.billboard.core.view.BaseActivity

class NowPlayingActivity : BaseActivity() {
    override fun onRequestFragment() = NowPlayingFragment.newInstance()
}
