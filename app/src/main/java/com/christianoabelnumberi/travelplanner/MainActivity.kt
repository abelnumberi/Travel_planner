package com.christianoabelnumberi.travelplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.christianoabelnumberi.travelplanner.navigation.SetupNavGraph
import com.christianoabelnumberi.travelplanner.ui.theme.TravelPlannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TravelPlannerTheme {
                SetupNavGraph()
            }
        }
    }
}