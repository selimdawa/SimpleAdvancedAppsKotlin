package com.flatcode.simpleadvancedapps.valorant.data.repository

import com.flatcode.simpleadvancedapps.valorant.data.model.agents.AgentDetailResponse
import com.flatcode.simpleadvancedapps.valorant.data.model.agents.AgentResponse
import com.flatcode.simpleadvancedapps.valorant.data.remote.ValorantService
import com.flatcode.simpleadvancedapps.valorant.domain.repository.ValorantRepository

class ValorantRepositoryImpl(private val valorantService: ValorantService) : ValorantRepository {

    override suspend fun getAgents(): AgentResponse = valorantService.getAgents()

    override suspend fun getAgentByUuid(agentUuid: String): AgentDetailResponse =
        valorantService.getAgentByUuid(agentUuid)
}