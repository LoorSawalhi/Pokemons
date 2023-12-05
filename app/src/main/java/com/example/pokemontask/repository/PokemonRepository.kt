package com.example.pokemontask.repository


import com.example.pokemontask.network.Details
import com.example.pokemontask.network.PokemonList
import retrofit2.Response


interface PokemonRepository {
    fun getPokemons(offset: Int, limit: Int, callback: PokemonCallback<PokemonList, String>)
    fun getDetails(url: String, callback: PokemonCallback<Details, String>)
    fun getExtraDetails(url: String, callback: PokemonCallback<Any, String>)
}

interface PokemonCallback<Response, Error> {
    fun onSuccess(response: Response)
    fun onError(errorMessage: String)
}