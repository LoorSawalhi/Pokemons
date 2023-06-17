package com.example.pokemontask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemontask.adapter.PokemonListAdapter
import com.example.pokemontask.databinding.FragmentPokemonListBinding
import com.example.pokemontask.network.PokemonApi
import com.example.pokemontask.network.Pokemons
import kotlinx.coroutines.launch

class PokemonListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var pokemonAdapter: PokemonListAdapter
    private lateinit var searchView: SearchView
    private var _binding: FragmentPokemonListBinding? = null
    private val binding get() = _binding!!
    private var nextUrl: String? = null
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        val view = binding.root
        recyclerView = view.findViewById(R.id.photos_grid)
        searchView = view.findViewById(R.id.searchView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = null // Set initial adapter as null

        lifecycleScope.launch {
            val pokemonList = getPokemonList()
            pokemonAdapter = PokemonListAdapter(pokemonList, pokemonList)
            recyclerView.adapter = pokemonAdapter // Set the adapter once the data is available
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                pokemonAdapter.filterList(newText)
                return true
            }
        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                    lifecycleScope.launch {
                        loadData(nextUrl)
                    }
                }
            }
        })
        return view
    }

    private suspend fun loadData(nextUrl: String?) {
        val pokemonsList = nextUrl?.let { PokemonApi.retrofitService.getMorePokemons(it) }
        if (pokemonsList != null) {
            this.nextUrl = pokemonsList.next
            pokemonAdapter.updateList(pokemonsList.results)
        }
    }

    private suspend fun getPokemonList(): List<Pokemons> {
        val pokemonList = PokemonApi.retrofitService.getPokemon()
        nextUrl = pokemonList.next
        return pokemonList.results
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}