package com.flatcode.simpleadvancedapps.dogs.api

import com.google.gson.annotations.SerializedName

data class DogApi(@SerializedName("message") val images: List<String>)