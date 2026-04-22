package com.christianoabelnumberi.travelplanner.ui.screen

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.text.NumberFormat
import java.util.Locale

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

    val total = budgetInt * faktorKota * faktorTransport

    val biayaTransport = total * 0.5
    val biayaMakan = total * 0.3
    val biayaLain = total * 0.2

    val rupiah = NumberFormat.getCurrencyInstance(Locale("in", "ID"))

    val hasilShare = "Trip ke $tujuan membutuhkan ${rupiah.format(total)}"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hasil Perjalanan") },

                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                },

                actions = {
                    IconButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, hasilShare)
                            }
                            context.startActivity(
                                Intent.createChooser(intent, "Bagikan via")
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Share"
                        )
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

            // CARD UTAMA
            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF1F5FF)
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = tujuan,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(Modifier.height(8.dp))

                    Text("Transport: $transport")

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "Total Estimasi",
                        style = MaterialTheme.typography.labelMedium
                    )

                    Text(
                        text = rupiah.format(total),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // CARD RINCIAN
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Rincian Biaya",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(Modifier.height(12.dp))

                    ItemBiaya("Transport", rupiah.format(biayaTransport))
                    ItemBiaya("Makan", rupiah.format(biayaMakan))
                    ItemBiaya("Lain-lain", rupiah.format(biayaLain))
                }
            }
        }
    }
}

@Composable
fun ItemBiaya(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        Text(value)
    }
}