package com.example.pokemontask.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
interface PokemonsApi {
    @GET("pokemon")
    fun getPokemon(@Query("offset") offset: Int, @Query("limit") limit: Int) : Call<PokemonList>?

    @GET
    fun getDetails(@Url url: String) : Call<Details>?

    @GET
    fun getExtraDetails(@Url url: String) : Call<Any>
}
object PokemonApiObject {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
        .build()

    val retrofitService: PokemonsApi by lazy {
        retrofit.create(PokemonsApi::class.java)
    }
}

val pokemonApi: PokemonsApi = PokemonApiObject.retrofitService