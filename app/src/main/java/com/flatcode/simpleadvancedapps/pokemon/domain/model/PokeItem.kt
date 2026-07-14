package com.flatcode.simpleadvancedapps.pokemon.domain.model

import com.flatcode.simpleadvancedapps.utils.DATA.RAW_URL_POKE
import com.flatcode.simpleadvancedapps.pokemon.data.model.PokeModel
import java.util.*

data class PokeItem(val id: Int, val name: String, val img: String) {

    val formatId = "N° ${id.toString().padStart(3, '0')}"
}

fun PokeModel.toDomain(): PokeItem {
    val arrayUrl = url.split("/")
    val id = arrayUrl[arrayUrl.size - 2].toInt()
    val name = name.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
    val img = "$RAW_URL_POKE$id.png"
    return PokeItem(id, name, img)
}