package com.airpods.ultimate

import android.bluetooth.BluetoothAdapter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
    var battery by remember { mutableStateOf(BatteryParser.getBattery()) }

    LaunchedEffect(Unit) {
        val adapter = BluetoothAdapter.getDefaultAdapter()

        val device = adapter?.bondedDevices?.firstOrNull {
            it.name.contains("AirPods", true)
        }

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

        Text(
            text = "AirPods Ultimate",
            style = MaterialTheme.typography.headlineMedium
        )

        Card {
            Column(Modifier.padding(20.dp)) {
                Text("Status")

                Text(
                    if (connected) "Verbunden" else "Nicht verbunden",
                    color = if (connected) Color.Green else Color.Red
                )

                Text("Gerät: $deviceName")
            }
        }

        Card {
            Column(Modifier.padding(20.dp)) {
                Text("Batterie")

                Text("Links: ${battery.left}%")
                Text("Rechts: ${battery.right}%")
                Text("Case: ${battery.case}%")
            }
        }
    }
}
