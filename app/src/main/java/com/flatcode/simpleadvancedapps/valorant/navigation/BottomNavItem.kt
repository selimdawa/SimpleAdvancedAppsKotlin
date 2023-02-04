package com.flatcode.simpleadvancedapps.valorant.navigation

import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.Unit.DATA

sealed class BottomNavItem(
    val title: String,
    val image: Int,
    val route: String
) {
    object Agents : BottomNavItem(
        title = DATA.CATEGORY_AGENTS,
        image = R.drawable.ic_agents,
        route = Screen.Agents.route
    )
}
