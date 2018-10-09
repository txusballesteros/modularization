package com.txusballesteros.codelabs.billboard.feature.nowplaying.domain.usecase

import com.txusballesteros.codelabs.billboard.feature.nowplaying.data.repository.NowPlayingRepository

class GetNowPlayingMoviesUseCase(
    private val repository: NowPlayingRepository
) {
    fun execute() = repository.getMovies()
}
