package com.example.pokemontask.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.example.pokemontask.R
import com.example.pokemontask.network.PokemonApi
import com.example.pokemontask.network.PokemonColor
import com.example.pokemontask.network.Pokemons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class PokemonListAdapter(
    private var pokemonList: List<Pokemons>?,
    private var list: List<Pokemons>?
) : RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val nameTextView: TextView = itemView.findViewById(R.id.name)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return PokemonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList?.get(position)
        val pokemonUrl = pokemon?.url
        var color: String = ""


        CoroutineScope(Dispatchers.Main).launch {
            val detailsList = pokemonUrl?.let { PokemonApi.retrofitService.getDetails(it) }
            val imageUrl = detailsList?.sprites?.front_default!!
            val extraDetails = PokemonApi.retrofitService.getExtraDetails(detailsList.species.url)
            val strings = extraDetails.toString().split(",")
            val colorString = strings[2].split("=")
            val imgUri = imageUrl.toUri().buildUpon().scheme("https").build()
            val foundColor = searchColorByName(colorString[2])
            color = colorString[2]
            val colorCode =
                foundColor?.hexCode ?: getRandomColorId(holder.itemView.context)

            Glide.with(holder.itemView)
                .load(imgUri)
                .into(holder.imageView)

            holder.nameTextView.text = pokemon.name
            holder.cardView.setCardBackgroundColor(colorCode)

            holder.cardView.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("NAME", pokemon.name)
                    putString("COLOR", color)
                }
                holder.itemView.findNavController().navigate(R.id.action_pokemonListFragment4_to_detailsFragment, bundle)
            }
        }
    }

    override fun getItemCount(): Int {
        return pokemonList?.size ?: 0
    }

    fun searchColorByName(colorName: String): PokemonColor? {
        return pokemonColors.find { it.name.equals(colorName, ignoreCase = true) }
    }

    fun getRandomColorId(context: Context): Int {
        val randomColor = pokemonColors.random()
        return ContextCompat.getColor(context, randomColor.hexCode)
    }

    fun filterList(query: String?) {
        val filteredList = if (query.isNullOrBlank()) {
            list
        } else {
            val lowercaseQuery = query.lowercase(Locale.ROOT)
            list?.filter { pokemon ->
                pokemon.name.lowercase(Locale.ROOT).contains(lowercaseQuery)
            }
        }
        updateFilteredList(filteredList)
    }

    private fun updateFilteredList(filteredList: List<Pokemons>?) {
        pokemonList = filteredList
        notifyDataSetChanged()
    }

    fun updateList(pokemonsList: List<Pokemons>) {
        if (pokemonsList.isNotEmpty()) {
            if (list != null) {
                if (!list!!.containsAll(pokemonsList)) {
                    list = list!!.plus(pokemonsList)
                    notifyDataSetChanged()
                }
            } else {
                list = pokemonsList
                notifyDataSetChanged()
            }
        }
    }
}

val pokemonColors = listOf(
    PokemonColor("Yellow", R.color.yellow),
    PokemonColor("Orange", R.color.orange),
    PokemonColor("Green", R.color.green),
    PokemonColor("Blue", R.color.blue),
    PokemonColor("Pink", R.color.pink),
    PokemonColor("Light Gray", R.color.light_gray),
    PokemonColor("Dark Gray", R.color.dark_gray),
    PokemonColor("Purple", R.color.purple),
    PokemonColor("Red", R.color.red),
    PokemonColor("Violet", R.color.violet),
    PokemonColor("Brown", R.color.brown)
)