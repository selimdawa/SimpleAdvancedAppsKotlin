package com.flatcode.simpleadvancedapps.valorant.navigation

sealed class Screen(val route: String) {
    object Agents : Screen("agents_screen")
    object AgentDetail : Screen("agent_detail_screen")
}