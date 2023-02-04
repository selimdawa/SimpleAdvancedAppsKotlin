package com.flatcode.simpleadvancedapps.valorant.domain.repository

import com.flatcode.simpleadvancedapps.valorant.data.model.agents.AgentDetailResponse
import com.flatcode.simpleadvancedapps.valorant.data.model.agents.AgentResponse

interface ValorantRepository {

    suspend fun getAgents(): AgentResponse

    suspend fun getAgentByUuid(agentUuid: String): AgentDetailResponse
}