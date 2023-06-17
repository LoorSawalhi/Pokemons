package com.example.pokemontask.network

import com.squareup.moshi.Json

data class PokemonColor(val name: String, val hexCode: Int)

data class PokemonList(
    @Json(name = "count") val count: Int,
    @Json(name = "next") val next: String?,
    @Json(name = "previous") val previous: String?,
    @Json(name = "results") val results: List<Pokemons>
)

data class Pokemons(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String,
)

data class Details(
    @Json(name = "abilities") val abilities: List<Any>?,
    @Json(name = "base_experience") val base_experience: Int?,
    @Json(name = "forms") val forms: List<Any>?,
    @Json(name = "game_indices") val game_indices: List<Any>?,
    @Json(name = "height") val height: Int,
    @Json(name = "held_items") val held_items: List<Any>?,
    @Json(name = "id") val id: Int,
    @Json(name = "is_default") val is_default: Boolean,
    @Json(name = "location_area_encounters") val location_area_encounters: String?,
    @Json(name = "moves") val moves: List<Any>?,
    @Json(name = "name") val name: String,
    @Json(name = "order") val order: Int,
    @Json(name = "past_types") val past_types: List<Any>?,
    @Json(name = "species") val species: Species,
    @Json(name = "sprites") val sprites: Sprites,
    @Json(name = "stats") val stats: List<PokemonStat>?,
    @Json(name = "types") val types: List<Any>?,
    @Json(name = "weight") val weight: Int
)

data class PokemonStat(
    @Json(name = "base_stat") val baseStat: Int,
    @Json(name = "effort") val effort: Int,
    @Json(name = "stat") val stat: Stat
)

data class Stat(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String
)

data class Species(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String,
)


data class Sprites(
    @Json(name = "back_default")val back_default: String?,
    @Json(name = "back_female") val back_female: String?,
    @Json(name = "back_shiny")val back_shiny: String?,
    @Json(name = "back_shiny_female")val back_shiny_female: String?,
    @Json(name = "front_default")val front_default: String?,
    @Json(name = "front_female")val front_female: String?,
    @Json(name = "front_shiny")val front_shiny: String?,
    @Json(name = "front_shiny_female")val front_shiny_female: String?,
    @Json(name = "other")val other: Other,
    @Json(name = "versions")val versions:  Any?)

data class Other(
    @Json(name = "dream_world") val dreamWorld: DreamWorld?,
    @Json(name = "home") val home: Home?,
    @Json(name = "official-artwork") val officialArtwork: OfficialArtwork?
)

data class DreamWorld(
    @Json(name = "front_default") val frontDefault: String?,
    @Json(name = "front_female") val frontFemale: String?
)

data class Home(
    @Json(name = "front_default") val frontDefault: String?,
    @Json(name = "front_female") val frontFemale: String?,
    @Json(name = "front_shiny") val frontShiny: String?,
    @Json(name = "front_shiny_female") val frontShinyFemale: String?
)

data class OfficialArtwork(
    @Json(name = "front_default") val frontDefault: String?,
    @Json(name = "front_shiny") val frontShiny: String?
)