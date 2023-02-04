package com.flatcode.simpleadvancedapps.valorant.domain.model

import com.flatcode.simpleadvancedapps.valorant.data.model.agents.Ability
import com.flatcode.simpleadvancedapps.valorant.data.model.agents.Role

data class Agent(
    val abilities: List<Ability>,
    val description: String,
    val displayIcon: String,
    val displayName: String,
    val fullPortrait: String,
    val role: Role?,
    val uuid: String
)