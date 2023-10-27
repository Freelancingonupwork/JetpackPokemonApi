package com.example.pokemonapi.`interface`

import com.example.pokemonapi.data.Pokemonmodel
import retrofit2.Call
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Headers

interface Retrofitservice {

    @Headers(
        "Accept: application/json"
            )
    @GET("pokemon/")
    suspend fun getPokemonList(): Response<Pokemonmodel?>?
}