package com.pokemon

import android.app.Application
import com.pokemon.di.component.AppComponent
import com.pokemon.di.component.DaggerAppComponent
import com.pokemon.di.module.NetworkModule
import com.pokemon.di.module.PokemonUsecaseModule
import com.pokemon.di.module.RepositoryModule

class BaseApp : Application() {
     val appComponent: AppComponent by lazy { initDagger() }


    private fun initDagger()  = DaggerAppComponent.builder()
        .networkModule(NetworkModule())
        .repositoryModule(RepositoryModule())
        .pokemonUsecaseModule(PokemonUsecaseModule())
        .build()

}