package com.airpods.ultimate

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                1
            )
        }

        setContent {
            MaterialTheme {
                AirPodsScreen(this)
            }
        }
    }
}

@Composable
fun AirPodsScreen(activity: ComponentActivity) {

    var deviceName by remember { mutableStateOf("Suche...") }
    var connected by remember { mutableStateOf(false) }
    var battery by remember { mutableStateOf<Int?>(null) }
    var showPopup by remember { mutableStateOf(false) }

    val fallback = BatteryHelper.getFallback()

    DisposableEffect(Unit) {

        val receiver = BluetoothReceiver { name, isConnected, level ->
            deviceName = name
            connected = isConnected
            battery = level

            NotificationHelper.show(activity, name)

            if (isConnected) showPopup = true
        }

        val filter = IntentFilter().apply {
            addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
            addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
            addAction(BluetoothDevice.ACTION_BATTERY_LEVEL_CHANGED)
        }

        activity.registerReceiver(receiver, filter)

        onDispose {
            activity.unregisterReceiver(receiver)
        }
    }

    Box(Modifier.fillMaxSize()) {

        Column(
            Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Text("AirPods Ultimate", style = MaterialTheme.typography.headlineMedium)

            Card(shape = RoundedCornerShape(30.dp)) {
                Column(Modifier.padding(20.dp)) {
                    Text(if (connected) "Verbunden" else "Nicht verbunden")
                    Text(deviceName)
                }
            }

            Card(shape = RoundedCornerShape(30.dp)) {
                Column(Modifier.padding(20.dp)) {

                    Text("Batterie")

                    Text("Links: ${battery ?: fallback.left}%")
                    Text("Rechts: ${battery ?: fallback.right}%")
                    Text("Case: ${battery ?: fallback.case}%")
                }
            }
        }

        // 🍎 POPUP
        AnimatedVisibility(
            visible = showPopup,
            enter = slideInVertically { it } + fadeIn(),
            exit = fadeOut()
        ) {

            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {

                Card(
                    shape = RoundedCornerShape(40.dp),
                    modifier = Modifier.padding(20.dp)
                ) {

                    Column(
                        Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(deviceName)
                        Text("${battery ?: "--"}%")
                    }
                }
            }
        }
    }
}
