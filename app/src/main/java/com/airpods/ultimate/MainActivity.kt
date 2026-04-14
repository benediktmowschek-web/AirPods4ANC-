package com.airpods.ultimate

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔥 Permission Fix (ANDROID 12+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                    1
                )
            }
        }

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
        try {
            val adapter = BluetoothAdapter.getDefaultAdapter()

            val device = adapter?.bondedDevices?.firstOrNull {
                it.name.contains("AirPods", true)
            }

            connected = device != null
            deviceName = device?.name ?: "Keine AirPods"

            if (connected) {
                battery = BatteryParser.getBattery()
            }

        } catch (e: Exception) {
            deviceName = "Fehler: ${e.message}"
            connected = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text("AirPods Ultimate", style = MaterialTheme.typography.headlineMedium)

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
