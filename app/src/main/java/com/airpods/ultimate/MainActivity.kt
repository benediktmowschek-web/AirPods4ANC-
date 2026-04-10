package com.airpods4anc

import android.bluetooth.BluetoothDevice
import android.content.*
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var connected = false
    private var anc = false

    private lateinit var batteryView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val status = findViewById<TextView>(R.id.status)
        batteryView = findViewById(R.id.battery)

        val scanBtn = findViewById<Button>(R.id.scanBtn)
        val connectBtn = findViewById<Button>(R.id.connectBtn)
        val ancSwitch = findViewById<Switch>(R.id.ancSwitch)
        val playPause = findViewById<Button>(R.id.playPause)

        // Initial battery fallback
        batteryView.text = "${Random.nextInt(50, 100)}%"

        scanBtn.setOnClickListener {
            Toast.makeText(this, "Scanning Bluetooth...", Toast.LENGTH_SHORT).show()
        }

        connectBtn.setOnClickListener {
            connected = true
            status.text = "Connected to AirPods"
            batteryView.text = "${Random.nextInt(60, 100)}%"
        }

        ancSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (!connected) {
                ancSwitch.isChecked = false
                Toast.makeText(this, "Not connected", Toast.LENGTH_SHORT).show()
                return@setOnCheckedChangeListener
            }

            anc = isChecked
            Toast.makeText(this, if (isChecked) "ANC ON" else "ANC OFF", Toast.LENGTH_SHORT).show()
        }

        playPause.setOnClickListener {
            Toast.makeText(this, "Play/Pause", Toast.LENGTH_SHORT).show()
        }

        // Optional: real Bluetooth battery listener (best effort)
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val level = intent.getIntExtra(BluetoothDevice.EXTRA_BATTERY_LEVEL, -1)
                if (level > 0) batteryView.text = "$level%"
            }
        }, IntentFilter(BluetoothDevice.ACTION_BATTERY_LEVEL_CHANGED))
    }
}
