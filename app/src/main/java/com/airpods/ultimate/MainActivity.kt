package com.airpods4anc

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    private lateinit var receiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(
                colorScheme = dynamicColorScheme()
            ) {
                AirPodsScreen()
            }
        }

        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                // trigger recomposition automatically
            }
        }

        val filter = IntentFilter().apply {
            addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
            addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        }

        registerReceiver(receiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}

@Composable
fun AirPodsScreen() {

    var deviceName by remember { mutableStateOf("Suche...") }
    var connected by remember { mutableStateOf(false) }
    var ancMode by remember { mutableStateOf("ANC") }

    var leftBattery by remember { mutableStateOf(0) }
    var rightBattery by remember { mutableStateOf(0) }
    var caseBattery by remember { mutableStateOf(0) }

    // 🔵 AirPods erkennen
    LaunchedEffect(Unit) {
        updateBluetooth { name, isConnected ->
            deviceName = name
            connected = isConnected

            if (isConnected) {
                // Fake realistisch (bis echte Daten verfügbar)
                leftBattery = (80..100
