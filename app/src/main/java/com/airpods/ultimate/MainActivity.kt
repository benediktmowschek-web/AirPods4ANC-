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
    var realBattery by remember { mutableStateOf<Int?>(null) }
    var showPopup by remember { mutableStateOf(false) }

    val battery = BatteryHelper.getSmartBattery(realBattery)

    DisposableEffect(Unit) {

        val receiver = BluetoothReceiver { name, isConnected ->
            deviceName = name
            connected = isConnected

            NotificationHelper.show(activity, name)

            if (isConnected) {
                showPopup = true
            }
        }

        val filter = IntentFilter().apply {
            addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
            addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        }

        activity.registerReceiver(receiver, filter)

        onDispose {
            activity.unregisterReceiver(receiver)
        }
    }

    Box(Modifier.fillMaxSize()) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Text(
                "AirPods",
                style = MaterialTheme.typography.headlineLarge
            )

            // 🔵 Status Card
            Card(
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E))
            ) {
                Column(Modifier.padding(20.dp)) {

                    Text("Status", color = Color.Gray)

                    Text(
                        if (connected) "Verbunden" else "Nicht verbunden",
                        color = if (connected) Color(0xFF30D158) else Color.Red
                    )

                    Text(deviceName, color = Color.White)
                }
            }

            // 🔋 Batterie Card
            Card(
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E))
            ) {
                Column(Modifier.padding(20.dp)) {

                    Text("Batterie", color = Color.Gray)

                    Text("Links: ${battery.left}%", color = Color.White)
                    Text("Rechts: ${battery.right}%", color = Color.White)
                    Text("Case: ${battery.case}%", color = Color.White)
                }
            }
        }

        // 🍎 POPUP (FINAL)
        AnimatedVisibility(
            visible = showPopup,
            enter = fadeIn() + scaleIn(initialScale = 0.9f),
            exit = fadeOut()
        ) {

            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Card(
                    shape = RoundedCornerShape(40.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF2C2C2E)
                    ),
                    modifier = Modifier.padding(20.dp)
                ) {

                    Column(
                        Modifier.padding(25.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            deviceName,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )

                        Spacer(Modifier.height(15.dp))

                        Text("L: ${battery.left}%", color = Color.Green)
                        Text("R: ${battery.right}%", color = Color.Green)
                        Text("Case: ${battery.case}%", color = Color.Green)
                    }
                }
            }
        }
    }
}
