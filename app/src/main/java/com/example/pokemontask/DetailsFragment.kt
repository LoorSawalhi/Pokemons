package com.example.pokemontask


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemontask.adapter.DetailsAdapter
import com.example.pokemontask.adapter.pokemonColors
import com.example.pokemontask.databinding.FragmentDetailsBinding
import com.example.pokemontask.network.Details
import com.example.pokemontask.network.PokemonApi
import com.example.pokemontask.network.PokemonColor
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {
    companion object {
        var NAME = "letter"
        var COLOR = "color"
    }

    private lateinit var pokemonName: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var detailsAdapter: DetailsAdapter
    private lateinit var color: String
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pokemonName = arguments?.getString("NAME").toString()
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        val view = binding.root
        val image: ImageView = view.findViewById(R.id.imageView)
        val name: TextView = view.findViewById(R.id.name)
        val weight: TextView = view.findViewById(R.id.weight)
        val toolBar: Toolbar = view.findViewById(R.id.toolbar)
        val length: TextView = view.findViewById(R.id.length)
        val backButton: ImageView = view.findViewById(R.id.back_button)
        val layout: LinearLayout = view.findViewById(R.id.pokemon_details)

        recyclerView = view.findViewById(R.id.photos_grid)

        name.text = pokemonName
        lifecycleScope.launch {
            val details = getDetailsList()
            println(details)
            detailsAdapter = DetailsAdapter(details)
            recyclerView.adapter = detailsAdapter

            val extraDetails = PokemonApi.retrofitService.getExtraDetails(details.species.url)
            val strings = extraDetails.toString().split(",")
            val colorString = strings[2].split("=")
            val foundColor = searchColorByName(colorString[2])
            val imageUrl = details.sprites.front_default
            val imgUri = imageUrl?.toUri()?.buildUpon()?.scheme("https")?.build()
            color = colorString[2]
            val colorCode =
                foundColor?.hexCode ?: getRandomColorId(layout.context)

            Glide.with(view)
                .load(imgUri)
                .into(image)

            layout.setBackgroundColor(colorCode)
            toolBar.setBackgroundColor(colorCode)
            weight.text = details.weight.toString()
            length.text = details.height.toString()

        }

        val layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = layoutManager

        backButton.setOnClickListener {
            backButton.findNavController().navigate(R.id.action_detailsFragment_to_pokemonListFragment4, null)
        }
        return view
    }

    private suspend fun getDetailsList(): Details {
        return PokemonApi.retrofitService.getDetails("https://pokeapi.co/api/v2/pokemon/"+pokemonName)
    }

    private fun searchColorByName(colorName: String): PokemonColor? {
        return pokemonColors.find { it.name.equals(colorName, ignoreCase = true) }
    }

    private fun getRandomColorId(context: Context): Int {
        val randomColor = pokemonColors.random()
        return ContextCompat.getColor(context, randomColor.hexCode)
    }
}