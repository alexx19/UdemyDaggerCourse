package com.pepefutetask.viewmodel

import androidx.lifecycle.MutableLiveData
import com.pepefutetask.data.PokemonDetails
import com.pepefutetask.data.PokemonResponse
import com.pepefutetask.domain.PokemonUsecase
import javax.inject.Inject

class PokeMonViewModel @Inject constructor(private val usecase: PokemonUsecase) : BaseViewModel() {

    private val pokemonListMutableLiveData = MutableLiveData<PokemonResponse>()
    private val pokemonDetailsMutableLiveData  = MutableLiveData<PokemonDetails>()

    fun getPokemonList() {
        if (pokemonListMutableLiveData.value != null) {
            return
        }
        val disposable = usecase.getPokemonList(0)
            .subscribe {
                pokemonListMutableLiveData.value = it
            }
        compositeDisposable.add(disposable)
    }


    fun getPokemonDetails(id:Int) {
        val disposable = usecase.getPokemonDetails(id)
            .subscribe {
                pokemonDetailsMutableLiveData.value = it
            }
        compositeDisposable.add(disposable)
    }

    fun getLivePokemonDetails() = pokemonDetailsMutableLiveData

    fun getLivePokemonList() = pokemonListMutableLiveData


}