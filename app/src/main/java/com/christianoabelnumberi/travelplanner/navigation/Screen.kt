package com.christianoabelnumberi.travelplanner.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Result : Screen("result/{tujuan}/{budget}/{transport}")

    fun createRoute(tujuan: String, budget: String, transport: String): String {
        return "result/${Uri.encode(tujuan)}/${Uri.encode(budget)}/${Uri.encode(transport)}"
    }
}