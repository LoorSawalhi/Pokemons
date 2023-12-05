package com.example.pokemontask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemontask.adapter.PokemonListAdapter
import com.example.pokemontask.network.PokemonList
import com.example.pokemontask.network.pokemonApi
import com.example.pokemontask.repository.PokemonCallback
import com.example.pokemontask.repository.PokemonRepositoryImpl

class PokemonListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var pokemonAdapter: PokemonListAdapter
    private lateinit var searchView: SearchView
    private var isLoading = false
    private var offset = 0
    private val pageSize = 20
    private val pokemonRepository = PokemonRepositoryImpl(pokemonApi)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pokemon_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.photos_grid)
        searchView = view.findViewById(R.id.searchView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = null

        pokemonRepository.getPokemons(offset, pageSize, object : PokemonCallback<PokemonList, String> {

            override fun onSuccess(response: PokemonList) {
                isLoading = false
                val pokemonList = response!!
                pokemonAdapter = PokemonListAdapter(pokemonList.results, pokemonList.results)
                recyclerView.adapter = pokemonAdapter
                pokemonAdapter.updateList(pokemonList.results)            }

            override fun onError(errorMessage: String) {
                isLoading = false
            }
        })

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
                    offset += pageSize
                    loadPokemons()
                }
            }
        })
    }

    private fun loadPokemons() {
//        if (isLoading) return

        isLoading = true
        pokemonRepository.getPokemons(offset, pageSize, object :  PokemonCallback<PokemonList, String>{

            override fun onSuccess(response: PokemonList) {
                val pokemonList = response!!
                isLoading = false
                offset += pageSize
                pokemonAdapter.updateList(pokemonList.results)
            }

            override fun onError(errorMessage: String) {
                isLoading = false
            }
        })
    }
}