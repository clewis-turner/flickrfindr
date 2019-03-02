package com.clewis.flickrfindr.modules

import com.clewis.flickrfindr.network.FlickrClient
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object NetworkClientProvider {

    object FlickrValues {
        const val BASE_URL = "https://api.flickr.com/services/rest/?"
        const val API_KEY = "1508443e49213ff84d566777dc211f2a"
        const val FORMAT = "json"
    }

    val flickrClient = provideNetworkClient(provideRetrofit(provideGson(), provideOkHttpClient()))

    //////////////////////////////
    //Dependency Injected>
    //////////////////////////////
    private fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setLenient()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    private fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(FlickrValues.BASE_URL)
                .client(okHttpClient)
                .build()
    }

    private fun provideNetworkClient(retrofit: Retrofit): FlickrClient {
        return retrofit.create(FlickrClient::class.java)
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor { chain ->
            val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("format", FlickrValues.FORMAT)
                    .addQueryParameter("nojsoncallback", "1")
                    .addQueryParameter("api_key", FlickrValues.API_KEY)
                    .build()
            val request = chain.request().newBuilder().url(url).build()

            chain.proceed(request)
        }
        return builder.build()
    }
}