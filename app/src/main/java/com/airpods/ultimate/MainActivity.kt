package com.airpods4anc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    var connected by remember { mutableStateOf(false) }
    var ancMode by remember { mutableStateOf("ANC") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        // Titel
        Text(
            text = "AirPods Ultimate",
            style = MaterialTheme.typography.headlineMedium
        )

        // Verbindungsstatus
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text("Status")
                Text(if (connected) "Verbunden" else "Nicht verbunden")

                Spacer(modifier = Modifier.height(10.dp))

                Button(onClick = { connected = !connected }) {
                    Text("Verbinden")
                }
            }
        }

        // Batterie
        Card {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Batterie")
                Text("Links: 85%")
                Text("Rechts: 87%")
                Text("Case: 92%")
            }
        }

        // ANC Steuerung
        Card {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Geräuschkontrolle")

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(onClick = { ancMode = "ANC" }) {
                        Text("ANC")
                    }
                    Button(onClick = { ancMode = "Transparenz" }) {
                        Text("Transparenz")
                    }
                    Button(onClick = { ancMode = "Aus" }) {
                        Text("Aus")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text("Aktiv: $ancMode")
            }
        }
    }
}
