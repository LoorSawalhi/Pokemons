package com.example.pokemontask.services

import com.example.pokemontask.network.Details
import com.example.pokemontask.network.PokemonList
import com.example.pokemontask.repository.PokemonCallback
import com.example.pokemontask.repository.PokemonRepository

class PokemonService(private val pokemonRepository: PokemonRepository) {
    fun getPokemons(offset: Int, limit: Int, callback: PokemonCallback) {
        pokemonRepository.getPokemons(offset, limit, object : PokemonCallback {
            override fun onPokemonsLoaded(pokemonList: PokemonList) {
                callback.onPokemonsLoaded(pokemonList)
            }

            override fun onDetailsLoaded(details: Details) {
            }

            override fun onExtraDetailsLoaded(extraDetails: Any) {
            }

            override fun onError(errorMessage: String) {
                callback.onError(errorMessage)
            }
        })
    }

    fun getDetails(url: String, callback: PokemonCallback) {
        pokemonRepository.getDetails(url, object : PokemonCallback {
            override fun onPokemonsLoaded(pokemonList: PokemonList) {
            }

            override fun onDetailsLoaded(details: Details) {
                callback.onDetailsLoaded(details)
            }

            override fun onExtraDetailsLoaded(extraDetails: Any) {
            }

            override fun onError(errorMessage: String) {
                callback.onError(errorMessage)
            }
        })
    }

    fun getExtraDetails(url: String, callback: PokemonCallback) {
        pokemonRepository.getExtraDetails(url, object : PokemonCallback {
            override fun onPokemonsLoaded(pokemonList: PokemonList) {
            }

            override fun onDetailsLoaded(details: Details) {
            }

            override fun onExtraDetailsLoaded(extraDetails: Any) {
                callback.onExtraDetailsLoaded(extraDetails)
            }

            override fun onError(errorMessage: String) {
                callback.onError(errorMessage)
            }
        })
    }
}