package com.flatcode.simpleadvancedapps.valorant.presentation.agent.agents

import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.valorant.domain.model.Agent

data class AgentsState(
    val isLoading: Boolean = false,
    val agents: List<Agent> = emptyList(),
    val error: String = DATA.EMPTY
)