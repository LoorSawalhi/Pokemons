package com.example.pokemontask.repository

import com.example.pokemontask.network.Details
import com.example.pokemontask.network.PokemonList

interface PokemonRepository {
    fun getPokemons(offset: Int, limit: Int, callback: PokemonCallback)
    fun getDetails(url: String, callback: PokemonCallback)
    fun getExtraDetails(url: String, callback: PokemonCallback)
}

interface PokemonCallback {
    fun onPokemonsLoaded(pokemonList: PokemonList)
    fun onDetailsLoaded(details: Details)
    fun onError(errorMessage: String)
    fun onExtraDetailsLoaded(extraDetails: Any)
}