package com.flatcode.simpleadvancedapps.valorant.presentation.agent.agentdetail

import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.valorant.domain.model.Agent

data class AgentDetailState constructor(
    val isLoading: Boolean = false,
    val agent: Agent? = null,
    val error: String = DATA.EMPTY,
)