package com.flatcode.simpleadvancedapps.valorant.domain.usecase.agents

import com.flatcode.simpleadvancedapps.valorant.common.Resource
import com.flatcode.simpleadvancedapps.valorant.data.model.agents.toAgent
import com.flatcode.simpleadvancedapps.valorant.domain.model.Agent
import com.flatcode.simpleadvancedapps.valorant.domain.repository.ValorantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAgentsUseCase @Inject constructor(
    private val valorantRepository: ValorantRepository
) {
    operator fun invoke(): Flow<Resource<List<Agent>>> = flow {

        try {
            emit(Resource.Loading)
            valorantRepository.getAgents().data?.map { it.toAgent() }?.let {
                emit(Resource.Success(it))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage.orEmpty()))
        }
    }
}