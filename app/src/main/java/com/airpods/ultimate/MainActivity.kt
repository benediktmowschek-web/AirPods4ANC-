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

@Composable
fun AirPodsScreen() {

    var deviceName by remember { mutableStateOf("Suche...") }
    var connected by remember { mutableStateOf(false) }
    var ancMode by remember { mutableStateOf("ANC") }
    var battery by remember { mutableStateOf(BatteryParser.getBattery()) }

    LaunchedEffect(Unit) {
        val device = BluetoothManager.getAirPods()
        connected = device != null
        deviceName = device?.name ?: "Keine AirPods"

        if (connected) {
            battery = BatteryParser.getBattery()
        }
    }

    Column(
        Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text("AirPods Ultimate", style = MaterialTheme.typography.headlineMedium)

        AnimatedVisibility(visible = true) {
            Card(shape = RoundedCornerShape(24.dp)) {
                Column(Modifier.padding(20.dp)) {
                    Text("Status")
                    Text(
                        if (connected) "🟢 Verbunden" else "🔴 Nicht verbunden",
                        color = if (connected) Color.Green else Color.Red
                    )
                    Text("Gerät: $deviceName")
                }
            }
        }

        Card(shape = RoundedCornerShape(24.dp)) {
            Column(Modifier.padding(20.dp)) {
                Text("Batterie")
                BatteryBar("Links", battery.left)
                BatteryBar("Rechts", battery.right)
                BatteryBar("Case", battery.case)
            }
        }

        Card(shape = RoundedCornerShape(24.dp)) {
            Column(Modifier.padding(20.dp)) {

                Text("Geräuschkontrolle")

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

                Text("Aktiv: $ancMode")
            }
        }
    }
}

@Composable
fun BatteryBar(label: String, value: Int) {

    val progress by animateFloatAsState(value / 100f)

    Column(Modifier.fillMaxWidth()) {
        Text("$label: $value%")
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxWidth().height(10.dp)
        )
    }
}
