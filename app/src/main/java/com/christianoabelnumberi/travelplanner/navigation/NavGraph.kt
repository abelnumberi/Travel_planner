package com.christianoabelnumberi.travelplanner.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.christianoabelnumberi.travelplanner.ui.screen.MainScreen
import com.christianoabelnumberi.travelplanner.ui.screen.ResultScreen

@Composable
fun SetupNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {


        composable(Screen.Main.route) {
            MainScreen(navController)
        }


        composable(
            route = Screen.Result.route,
            arguments = listOf(
                navArgument("tujuan") { type = NavType.StringType },
                navArgument("budget") { type = NavType.StringType },
                navArgument("transport") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val tujuan = backStackEntry.arguments?.getString("tujuan") ?: ""
            val budget = backStackEntry.arguments?.getString("budget") ?: ""
            val transport = backStackEntry.arguments?.getString("transport") ?: ""

            ResultScreen(
                navController = navController,
                tujuan = tujuan,
                budget = budget,
                transport = transport
            )
        }
    }
}