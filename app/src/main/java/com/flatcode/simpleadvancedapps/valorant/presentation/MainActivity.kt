package com.flatcode.simpleadvancedapps.valorant.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.flatcode.simpleadvancedapps.valorant.navigation.BottomNavigationBar
import com.flatcode.simpleadvancedapps.valorant.navigation.NavGraph
import com.flatcode.simpleadvancedapps.valorant.navigation.Screen
import com.flatcode.simpleadvancedapps.valorant.presentation.theme.ValoBlue
import com.flatcode.simpleadvancedapps.valorant.presentation.theme.ValorantComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ValorantComposeTheme {
                val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                when (navBackStackEntry?.destination?.route) {
                    Screen.Agents.route -> bottomBarState.value = true
                    else -> bottomBarState.value = false
                }

                Scaffold(
                    backgroundColor = ValoBlue,
                    bottomBar = { BottomNavigationBar(navController, bottomBarState) }
                ) {
                    NavGraph(navController = navController, it)
                }
            }
        }
    }
}