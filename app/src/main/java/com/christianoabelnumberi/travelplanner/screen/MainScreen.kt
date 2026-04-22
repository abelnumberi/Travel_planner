package com.christianoabelnumberi.travelplanner.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.christianoabelnumberi.travelplanner.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {

    var tujuan by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }
    var transport by remember { mutableStateOf("Mobil") }
    var error by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Travel Planner") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            // INPUT TUJUAN
            OutlinedTextField(
                value = tujuan,
                onValueChange = { tujuan = it },
                label = { Text("Tujuan") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // INPUT BUDGET
            OutlinedTextField(
                value = budget,
                onValueChange = { budget = it },
                label = { Text("Budget") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // RADIO BUTTON
            Text(
                text = "Transportasi",
                style = MaterialTheme.typography.titleMedium
            )

            listOf("Mobil", "Motor", "Pesawat").forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (item == transport),
                            onClick = { transport = item }
                        )
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = (item == transport),
                        onClick = { transport = item }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = item)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ERROR MESSAGE
            if (error.isNotEmpty()) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // BUTTON PROSES
            Button(
                onClick = {
                    if (tujuan.isEmpty() || budget.isEmpty()) {
                        error = "Semua field harus diisi!"
                    } else {
                        error = ""

                        navController.navigate(
                            Screen.Main.createRoute(
                                tujuan = tujuan,
                                budget = budget,
                                transport = transport
                            )
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Proses")
            }
        }
    }
}