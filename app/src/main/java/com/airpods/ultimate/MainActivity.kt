package com.airpods.ultimate

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startService(Intent(this, AirPodsService::class.java))

        setContent {
            MaterialTheme {
                AirPodsScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirPodsScreen() {

    var deviceName by remember { mutableStateOf("Suche...") }
    var connected by remember { mutableStateOf(false) }
    var ancMode by remember { mutableStateOf("ANC") }
    var battery by remember { mutableStateOf(BatteryParser.getBattery()) }

    // 🔵 Auto Update
    LaunchedEffect(Unit) {
        val device = BluetoothManager.getAirPods()
        connected = device != null
        deviceName = device?.name ?: "Keine AirPods"

        if (connected) {
            battery = BatteryParser.getBattery()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        // 🏷️ Titel
        Text(
            text = "AirPods Ultimate",
            style = MaterialTheme.typography.headlineMedium
        )

        // 🔵 STATUS (animiert)
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(Modifier.padding(20.dp)) {

                    Text("Status")

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        if (connected) "🟢 Verbunden" else "🔴 Nicht verbunden",
                        color = if (connected) Color.Green else Color.Red
                    )

                    Text("Gerät: $deviceName")
                }
            }
        }

        // 🔋 BATTERIE
        Card(
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(Modifier.padding(20.dp)) {

                Text("Batterie")

                Spacer(modifier = Modifier.height(10.dp))

                BatteryBar("Links", battery.left)
                BatteryBar("Rechts", battery.right)
                BatteryBar("Case", battery.case)
            }
        }

        // 🎧 ANC CONTROL
        Card(
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(Modifier.padding(20.dp)) {

                Text("Geräuschkontrolle")

                Spacer(modifier = Modifier.height(10.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                    FilterChip(
                        selected = ancMode == "ANC",
                        onClick = { ancMode = "ANC" },
                        label = { Text("ANC") }
                    )

                    FilterChip(
                        selected = ancMode == "Transparenz",
                        onClick = { ancMode = "Transparenz" },
                        label = { Text("Transparenz") }
                    )

                    FilterChip(
                        selected = ancMode == "Aus",
                        onClick = { ancMode = "Aus" },
                        label = { Text("Aus") }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text("Aktiv: $ancMode")
            }
        }
    }
}

@Composable
fun BatteryBar(label: String, value: Int) {

    val progress by animateFloatAsState(value / 100f)

    Column(modifier = Modifier.fillMaxWidth()) {

        Text("$label: $value%")

        Spacer(modifier = Modifier.height(4.dp))

        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}
