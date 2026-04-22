package com.christianoabelnumberi.travelplanner.ui.screen

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    navController: NavHostController,
    tujuan: String,
    budget: String,
    transport: String
) {

    val context = LocalContext.current

    val budgetInt = budget.toIntOrNull() ?: 0


    val faktorKota = when (tujuan.lowercase()) {
        "bali" -> 1.5
        "jakarta" -> 1.3
        "bandung" -> 1.2
        "yogyakarta" -> 1.1
        else -> 1.0
    }


    val faktorTransport = when (transport) {
        "Mobil" -> 1.1
        "Motor" -> 1.05
        "Pesawat" -> 1.8
        else -> 1.0
    }

    val totalEstimasi = budgetInt * faktorKota * faktorTransport


    val biayaTransport = totalEstimasi * 0.5
    val biayaMakan = totalEstimasi * 0.3
    val biayaLain = totalEstimasi * 0.2

    val rupiah = NumberFormat.getCurrencyInstance(Locale("in", "ID"))

    val hasil = """
Perjalanan ke $tujuan

Total Estimasi: ${rupiah.format(totalEstimasi)}

Rincian:
- Transport: ${rupiah.format(biayaTransport)}
- Makan: ${rupiah.format(biayaMakan)}
- Lain-lain: ${rupiah.format(biayaLain)}
""".trimIndent()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hasil Perjalanan") },

                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },

                actions = {
                    IconButton(onClick = {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, hasil)
                        }
                        context.startActivity(
                            Intent.createChooser(intent, "Bagikan via")
                        )
                    }) {
                        Icon(Icons.Filled.Share, contentDescription = "Share")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                text = hasil,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}