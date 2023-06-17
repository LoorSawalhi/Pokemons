package com.example.pokemontask.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

private const val BASE_URL = "https://pokeapi.co/api/v2/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface PokemonApiService {
    @GET("pokemon?limit=20&offset=0")
    suspend fun getPokemon() : PokemonList
    @GET
    suspend fun getMorePokemons(@Url url: String) : PokemonList

    @GET
    suspend fun getDetails(@Url url: String) : Details

    @GET
    suspend fun getExtraDetails(@Url url: String) : Any
}

object PokemonApi {
    val retrofitService : PokemonApiService by lazy {
        retrofit.create(PokemonApiService::class.java) }
}
