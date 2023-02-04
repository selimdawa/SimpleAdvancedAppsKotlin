package com.flatcode.simpleadvancedapps.Unit

import com.flatcode.simpleadvancedapps.movies.MainActivity
import com.flatcode.simpleadvancedapps.movies.data.room.repository.MoviesRepositoryRealization

object DATA {

    //Main
    const val DOGS = "Dogs"
    const val COUNTRIES = "Countries"
    const val Calculator = "Calculator"
    const val CRYPTO = "Crypto"
    const val DICTIONARY = "Dictionary"
    const val MEALS = "Meals"
    const val VALORANT = "Valorant"
    const val POP = "Pop"
    const val MOVIE = "Movies"
    const val STOCK_MARKET = "Stock Market"
    const val NEWS = "News"
    const val RICK_AND_MORTY = "Rick & Morty"
    const val WEATHER = "Weather"
    const val POKE = "Poke"
    const val TODO_NOTE = "TODO-Note"

    //Class Name
    const val Country_details = "Country Details"
    const val Crypto_details = "Crypto Details"
    const val meaning_of_the_word = "Meaning Of The Word"
    const val Category_Meals = "Category Meals"
    const val Favorite_movies = "Favorite movies"
    const val Details_Movie = "Details Movie"
    const val Details_Poke = "Details Poke"

    //Other
    const val EMPTY = ""
    const val SPACE = " "
    const val Unknown = "Unknown"

    //Calculator
    const val Zero = "0"
    const val One = "1"
    const val Two = "2"
    const val Three = "3"
    const val Four = "4"
    const val Five = "5"
    const val Six = "6"
    const val Seven = "7"
    const val Eight = "8"
    const val Nine = "9"
    const val DOT = "."
    const val divide = "/"
    const val multiply = "*"
    const val minus = "-"
    const val plus = "+"

    //Dictionary
    const val DICTIONARY_KEY = "WORD_DEFINITION"
    const val Short_Def = "shortdef"
    const val DICTIONARY_API_KEY = "a13b6fd3-80c2-44de-a1a4-d40b14184662"
    const val DICTIONARY_BASIC_URL =
        "https://www.dictionaryapi.com/api/v3/references/learners/json/"

    //Crypto
    const val BASE_URL_CRYPTO = "https://pro-api.coinmarketcap.com/"
    const val API_KEY_CRYPTO = "e15a2a51-07b1-4d7c-bbff-ae29b8df3b29"
    const val LIMIT_CRYPTO = "10"

    //Rick & Morty
    const val Alive = "Alive"

    //Country
    const val COUNTRY_GSON: String =
        "atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json"
    const val BASE_URL_COUNTRY = "https://raw.githubusercontent.com/"

    //Crypto
    const val IMAGE_CRYPTO = "https://s2.coinmarketcap.com/static/img/coins/64x64/"
    const val LATEST_CRYPTO = "v1/cryptocurrency/listings/latest"
    const val INFO_CRYPTO = "v2/cryptocurrency/info"

    //Dogs
    const val BASE_URL_DOGS = "https://dog.ceo/api/breed/"

    //Meals
    const val BASE_URL_MEALS = "https://www.themealdb.com/api/json/v1/1/"

    //Movies
    const val BASE_URL_MOVIES = "https://api.themoviedb.org/"
    const val popular_MOVIES =
        "3/movie/popular?api_key=a036dc05c534b0cd90d6e8a8e2bcf871&language=en-US&page=1"
    const val IMAGE_MOVIE = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2"
    const val IMAGE_MOVIE_BASIC = "https://image.tmdb.org/t/p/w185/"
    lateinit var MAIN: MainActivity
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

    //Rick & Morty
    const val BASE_URL_RICK_AND_MORTY = "https://rickandmortyapi.com/api/"

    //Stock Market
    const val API_KEY_STOCK_MARKET = "CY75KFHR7APO4MSF"
    const val BASE_URL_STOCK_MARKET = "https://alphavantage.co"

    //Weather
    const val API_KEY_WEATHER = "ba5131fdd64a4584981104211223006"
    const val BASE_URL_WEATHER = "https://api.weatherapi.com/v1/forecast.json?key="

    //Valorant
    const val BASE_URL_VALORANT = "https://valorant-api.com/"
    const val CATEGORY_AGENTS = "Agents"
    const val PARAM_AGENT_ID = "agentUuid"
}