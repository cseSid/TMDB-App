package org.sid.tmdbapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import org.sid.tmdbapp.data.remote.api.TmdbApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(): TmdbApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbApiService::class.java)
    }

}

class HeaderInterceptor : Interceptor {

    //Ideally this token is coming from auth , so it will not exposed to git
    private val AUTH_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwNDc3NTQ4MWNmOWM2MzYwZWI0NzgwYWNlMzE3ZDg2MSIsIm5iZiI6MTcyNTM5NDYwMS41NTkxODgsInN1YiI6IjY2ZDc2ZGM2MjY3MjM4NjA0MzlkMDk5MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.zp17HLT4DoCGp45lfI2VIDbZzhNc4GjAuRtvwcGWxXs"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $AUTH_TOKEN")
            .addHeader("accept", "application/json")
            .build()
        return chain.proceed(request)
    }
}