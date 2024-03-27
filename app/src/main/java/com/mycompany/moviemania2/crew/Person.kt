package com.mycompany.moviemania2.crew

data class Person(
    val _links: Links,
    val birthday: String,
    val country: Country,
    val deathday: Any,
    val gender: String,
    val id: Int,
    val image: Image,
    val name: String,
    val updated: Int,
    val url: String
)