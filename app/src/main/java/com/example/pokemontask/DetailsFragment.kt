package com.example.pokemontask


import android.content.Context
import android.os.Bundle
import android.util.Log
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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemontask.adapter.DetailsAdapter
import com.example.pokemontask.adapter.pokemonColors
import com.example.pokemontask.databinding.FragmentDetailsBinding
import com.example.pokemontask.network.*
import com.example.pokemontask.repository.PokemonCallback
import com.example.pokemontask.repository.PokemonRepositoryImpl
import retrofit2.Response

private const val ARG_NAME = "NAME"

class DetailsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var detailsAdapter: DetailsAdapter
    private lateinit var color: String
    private lateinit var _binding: FragmentDetailsBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val pokemonName = arguments?.getString(ARG_NAME)
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        val view = binding.root
        val image: ImageView = view.findViewById(R.id.imageView)
        val name: TextView = view.findViewById(R.id.name)
        val weight: TextView = view.findViewById(R.id.weight)
        val toolBar: Toolbar = view.findViewById(R.id.toolbar)
        val length: TextView = view.findViewById(R.id.length)
        val backButton: ImageView = view.findViewById(R.id.back_button)
        val layout: LinearLayout = view.findViewById(R.id.pokemon_details)
        val pokemonRepository = PokemonRepositoryImpl(pokemonApi)

        recyclerView = view.findViewById(R.id.photos_grid)
        name.text = pokemonName

        pokemonRepository.getDetails("https://pokeapi.co/api/v2/pokemon/"+pokemonName, object :
            PokemonCallback<Details, String> {


            override fun onSuccess(response: Details) {

                val details = response!!
                detailsAdapter = DetailsAdapter(details)
                recyclerView.adapter = detailsAdapter

                val imageUrl = details.sprites.front_default
                val imgUri = imageUrl?.toUri()?.buildUpon()?.scheme("https")?.build()

                Glide.with(requireContext())
                    .load(imgUri)
                    .into(image)

                weight.text = details.weight.toString()
                length.text = details.height.toString()

                pokemonRepository.getExtraDetails(details.species.url,  object : PokemonCallback<Any, String> {

                    override fun onSuccess(response: Any) {
                        val extraDetails = response
                        val strings = extraDetails.toString().split(",")
                        val colorString = strings[2].split("=")
                        val foundColor = searchColorByName(colorString[2])

                        color = colorString[2]
                        val colorCode =
                            foundColor?.hexCode ?: getRandomColorId(layout.context)

                        layout.setBackgroundColor(colorCode)
                        toolBar.setBackgroundColor(colorCode)                    }

                    override fun onError(errorMessage: String) {
                        Log.e("ERROR", "Error: ${response}")
                    }
            }) }

            override fun onError(errorMessage: String) {
                Log.e("ERROR", "Error: ${errorMessage}")
            }
        })

        val layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = layoutManager

        backButton.setOnClickListener {
            backButton.findNavController().navigate(R.id.action_detailsFragment_to_pokemonListFragment4, null)
        }
        return view
    }

    private fun searchColorByName(colorName: String): PokemonColor? {
        return pokemonColors.find { it.name.equals(colorName, ignoreCase = true) }
    }

    private fun getRandomColorId(context: Context): Int {
        val randomColor = pokemonColors.random()
        return ContextCompat.getColor(context, randomColor.hexCode)
    }
}