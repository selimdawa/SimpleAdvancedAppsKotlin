package com.flatcode.simpleadvancedapps.valorant.data.remote

import com.flatcode.simpleadvancedapps.valorant.data.model.agents.AgentDetailResponse
import com.flatcode.simpleadvancedapps.valorant.data.model.agents.AgentResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ValorantService {

    @GET("v1/agents/?isPlayableCharacter=true")
    suspend fun getAgents(): AgentResponse

    @GET("v1/agents/{agentUuid}")
    suspend fun getAgentByUuid(@Path("agentUuid") agentUuid: String): AgentDetailResponse
}