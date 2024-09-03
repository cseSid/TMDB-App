package org.sid.tmdbapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sid.tmdbapp.data.remote.MovieRemoteDataSource
import org.sid.tmdbapp.data.remote.api.TmdbApiService
import org.sid.tmdbapp.data.repository.MovieRepository
import org.sid.tmdbapp.data.repository.MoviesRepoImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(remoteDataSource: MovieRemoteDataSource): MovieRepository {
        return MoviesRepoImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideMovieRemoteDataSource(apiService: TmdbApiService): MovieRemoteDataSource {
        return MovieRemoteDataSource(apiService)
    }


}