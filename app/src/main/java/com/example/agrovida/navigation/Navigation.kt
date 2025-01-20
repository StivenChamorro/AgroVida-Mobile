package com.example.agrovida.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.agrovida.screens.FarmScreen
import com.example.agrovida.screens.HomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController)
        }
        composable("farm") {
            FarmScreen(
                navController = navController,
                onMenuClick = {
                    // Aquí defines la acción a realizar cuando se haga clic en el menú
                }
            )
        }
//        composable("profile") {
//            ProfileScreen(navController)
//        }
//        composable("notifications") {
//            NotificationsScreen(navController)
//        }
//        composable("settings") {
//            SettingsScreen(navController)
//        }
//        composable("help") {
//            HelpScreen(navController)
//        }
    }
}

