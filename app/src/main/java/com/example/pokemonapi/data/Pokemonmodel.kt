package com.example.pokemonapi.data

data class Pokemonmodel(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)