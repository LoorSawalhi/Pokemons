package com.example.pokemontask.repository
import android.util.Log
import com.example.pokemontask.network.Details
import com.example.pokemontask.network.PokemonList
import com.example.pokemontask.network.PokemonsApi
import com.example.pokemontask.network.pokemonApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class PokemonRepositoryImpl(private val pokemonsApi: PokemonsApi) : PokemonRepository {

    override fun getPokemons(offset: Int, limit: Int, callback: PokemonCallback) {
        pokemonsApi.getPokemon(offset, limit)?.enqueue(object : Callback<PokemonList> {
            override fun onResponse(call: Call<PokemonList>, response: Response<PokemonList>) {
                if (response.isSuccessful) {
                    val pokemonList = response.body()
                    if (pokemonList != null) {
                        callback.onPokemonsLoaded(pokemonList)
                    }
                } else {
                    callback.onError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<PokemonList>, t: Throwable) {
                callback.onError("Error: ${t.message}")
            }
        })
    }

    override fun getDetails(url: String, callback: PokemonCallback) {
        pokemonApi.getDetails(url)?.enqueue(object : Callback<Details> {
            override fun onResponse(call: Call<Details>, response: Response<Details>) {
                if (response.isSuccessful) {
                    val details = response.body()
                    if (details != null) {
                        callback.onDetailsLoaded(details)
                    }
                } else {
                    callback.onError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Details>, t: Throwable) {
                callback.onError("Error: ${t.message}")
            }
        })
    }

    override fun getExtraDetails(url: String, callback: PokemonCallback) {
        pokemonApi.getExtraDetails(url)?.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    val extraDetails = response.body()
                    if (extraDetails != null) {
                        callback.onExtraDetailsLoaded(extraDetails)
                    }
                } else {
                    callback.onError("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                callback.onError("Error: ${t.message}")
            }
        })
    }
}