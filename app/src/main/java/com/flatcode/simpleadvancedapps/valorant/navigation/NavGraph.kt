package com.flatcode.simpleadvancedapps.valorant.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.flatcode.simpleadvancedapps.valorant.presentation.MainActivity
import com.flatcode.simpleadvancedapps.valorant.presentation.agent.agentdetail.AgentDetailScreen
import com.flatcode.simpleadvancedapps.valorant.presentation.agent.agents.AgentsScreen

@Composable
fun NavGraph(navController: NavHostController, paddingValues: PaddingValues) {

    NavHost(
        navController = navController,
        startDestination = Screen.Agents.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(route = Screen.Agents.route) {
            MainActivity()
        }

        composable(route = Screen.Agents.route) {
            AgentsScreen(
                navigateToAgentDetail = {
                    navController.navigate("${Screen.AgentDetail.route}/$it")
                }
            )
        }

        composable(route = "${Screen.AgentDetail.route}/{agentUuid}") {
            AgentDetailScreen()
        }
    }
}