package com.flatcode.simpleadvancedapps.utils

import com.flatcode.simpleadvancedapps.movies.data.room.repository.MoviesRepositoryRealization

object DATA {

    //Main
    const val DOGS = "Dogs"
    const val COUNTRIES = "Countries"
    const val CALCULATOR = "Calculator"
    const val CRYPTO = "Crypto"
    const val DICTIONARY = "Dictionary"
    const val MEALS = "Meals"
    const val POP = "Pop"
    const val MOVIE = "Movies"
    const val NEWS = "News"
    const val RICK_AND_MORTY = "Rick & Morty"
    const val WEATHER = "Weather"
    const val POKE = "Poke"
    const val TODO_NOTE = "TODO-Note"

    //Class Name
    const val COUNTRY_DETAILS = "Country Details"
    const val CRYPTO_DETAILS = "Crypto Details"
    const val MEANING_OF_THE_WORD = "Meaning Of The Word"
    const val CATEGORY_MEALS = "Category Meals"
    const val FAVORITE_MOVIES = "Favorite movies"
    const val DETAILS_MOVIE = "Details Movie"
    const val DETAILS_POKE = "Details Poke"

    //Other
    const val EMPTY = ""
    const val SPACE = " "
    const val UNKNOWN = "Unknown"

    //Calculator
    const val ZERO = "0"
    const val ONE = "1"
    const val TWO = "2"
    const val THREE = "3"
    const val FOUR = "4"
    const val FIVE = "5"
    const val SIX = "6"
    const val SEVEN = "7"
    const val EIGHT = "8"
    const val NINE = "9"
    const val DOT = "."
    const val DIVIDE = "/"
    const val MULTIPLY = "*"
    const val MINUS = "-"
    const val PLUS = "+"

    //Dictionary
    const val DICTIONARY_KEY = "WORD_DEFINITION"
    const val SHORT_DEF = "shortdef"
    const val DICTIONARY_API_KEY = "a13b6fd3-80c2-44de-a1a4-d40b14184662"
    const val DICTIONARY_BASIC_URL =
        "https://www.dictionaryapi.com/api/v3/references/learners/json/"

    //Crypto
    const val BASE_URL_CRYPTO = "https://pro-api.coinmarketcap.com/"
    const val API_KEY_CRYPTO = "e15a2a51-07b1-4d7c-bbff-ae29b8df3b29"
    const val LIMIT_CRYPTO = "10"
    const val IMAGE_CRYPTO = "https://s2.coinmarketcap.com/static/img/coins/128x128/"

    @Suppress("UNUSED_VARIABLE", "unused")
    const val LATEST_CRYPTO = "v1/cryptocurrency/listings/latest"
    const val INFO_CRYPTO = "v2/cryptocurrency/info"

    //Rick & Morty
    const val ALIVE = "Alive"
    const val BASE_URL_RICK_AND_MORTY = "https://rickandmortyapi.com/api/"

    //Country
    const val COUNTRY_GSON: String =
        "atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json"
    const val BASE_URL_COUNTRY = "https://raw.githubusercontent.com/"

    //Dogs
    const val BASE_URL_DOGS = "https://dog.ceo/api/breed/"

    //Meals
    const val BASE_URL_MEALS = "https://www.themealdb.com/api/json/v1/1/"

    //Movies
    const val BASE_URL_MOVIES = "https://api.themoviedb.org/"
    const val POPULAR_MOVIES =
        "3/movie/popular?api_key=a036dc05c534b0cd90d6e8a8e2bcf871&language=en-US&page=1"
    const val IMAGE_MOVIE = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2"
    const val IMAGE_MOVIE_BASIC = "https://image.tmdb.org/t/p/w185/"
    lateinit var REALIZATION: MoviesRepositoryRealization

    //News
    const val BASE_URL_NEWS = "https://newsapi.org/v2/"
    const val API_NEWS = "45df755913c947ea82988b1dad81c6e7"

    //Poke
    const val BASE_URL_POKE = "https://pokeapi.co/api/v2/"
    const val RAW_URL_POKE =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"

    //Pop
    const val BASE_URL_POP = "https://funko.fandom.com/wiki/Pop!_Animation"
    const val IMAGE_POP = "https://www.vectorkhazana.com/assets/images/products/Funko_Pup.png"

    //Weather
    const val API_KEY_WEATHER = "aadc41a523b744b483c154258230510"
    const val BASE_URL_WEATHER = "https://api.weatherapi.com/v1/forecast.json?key="
}