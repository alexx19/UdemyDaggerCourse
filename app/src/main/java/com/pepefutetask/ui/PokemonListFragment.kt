package com.pepefutetask.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pepefutetask.POKEMON_DETAILS_KEY
import com.pepefutetask.R
import com.pepefutetask.data.PokemonResponse
import com.pepefutetask.viewmodel.PokeMonListViewModel
import kotlinx.android.synthetic.main.fragment_pokemon_list.*

class PokemonListFragment : BaseFragment(), OnClickListener {
    private val pokemonDetailsFragment = PokemonDetailsFragment()

    private lateinit var pokeMonListViewModel: PokeMonListViewModel
    val pokadapter = PokemonListAdapter()


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        pokeMonListViewModel = ViewModelProviders.of(this,viewModelFactory)[PokeMonListViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)
    }

    fun setupView(view: View) {
        val linearLayoutManager = LinearLayoutManager(context)
        pokadapter.setClickListener(this)
        pokemonList.apply {
            layoutManager = linearLayoutManager
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = pokadapter
        }

    }

    fun getPokemonListData() {
        pokeMonListViewModel.getPokemonList()
        observePokemonList()
    }

    fun setData(response: PokemonResponse?) {
        response?.results?.let { pokadapter.addPokmons(it) }
    }



    override fun onClick(position: Int, view: View) {

        getPokemonDetails(position+1)
    }

    fun observePokemonList() {
        pokeMonListViewModel.getLivePokemonList().observe(this, Observer {
            setData(it)
        })
    }


    override fun getLayoutById(): Int {
        return R.layout.fragment_pokemon_list
    }

     fun initUI(view: View) {
        setupView(view)
        getPokemonListData()
    }


    fun getPokemonDetails(id: Int) {
        val bundle = Bundle()
        bundle.putInt(POKEMON_DETAILS_KEY,id)
        pokemonDetailsFragment.arguments = bundle

        (activity as BaseActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.container, pokemonDetailsFragment)
            .addToBackStack(null)
            .commit()

    }
}
