import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AirPodsScreen() {

    var deviceName by remember { mutableStateOf("Suche...") }
    var connected by remember { mutableStateOf(false) }
    var ancMode by remember { mutableStateOf("ANC") }
    var battery by remember { mutableStateOf(BatteryParser.getBattery()) }

    // 🔵 Bluetooth Check
    LaunchedEffect(Unit) {
        val device = BluetoothManager.getAirPods()
        connected = device != null
        deviceName = device?.name ?: "Keine AirPods"
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

        // 🔵 Status Card
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(Modifier.padding(20.dp)) {

                Text("Status")

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    if (connected) "🟢 Verbunden" else "🔴 Nicht verbunden"
                )

                Text("Gerät: $deviceName")
            }
        }

        // 🔋 Batterie Card
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(Modifier.padding(20.dp)) {

                Text("Batterie")

                Spacer(modifier = Modifier.height(10.dp))

                BatteryBar("Links", battery.left)
                BatteryBar("Rechts", battery.right)
                BatteryBar("Case", battery.case)
            }
        }

        // 🎧 ANC Steuerung
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(6.dp)
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

    Column(modifier = Modifier.fillMaxWidth()) {

        Text("$label: $value%")

        Spacer(modifier = Modifier.height(4.dp))

        LinearProgressIndicator(
            progress = value / 100f,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}
