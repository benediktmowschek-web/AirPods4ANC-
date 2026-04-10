package com.airpods.ultimate

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    private lateinit var receiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                AirPodsScreen()
            }
        }

        // Bluetooth Listener
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                // UI updated automatisch über Compose
            }
        }

        val filter = IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED)
        registerReceiver(receiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}

@Composable
fun AirPodsScreen() {

    var deviceName by remember { mutableStateOf("Nicht verbunden") }
    var connected by remember { mutableStateOf(false) }
    var ancMode by remember { mutableStateOf("ANC") }

    // AirPods erkennen
    LaunchedEffect(Unit) {
        val adapter = BluetoothAdapter.getDefaultAdapter()
        val device = adapter?.bondedDevices?.firstOrNull {
            it.name.contains("AirPods", true)
        }

        deviceName = device?.name ?: "Keine AirPods"
        connected = device != null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(
