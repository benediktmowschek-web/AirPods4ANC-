package com.airpods4anc

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var connected = false
    private var ancEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val status = findViewById<TextView>(R.id.status)
        val battery = findViewById<TextView>(R.id.battery)
        val scanBtn = findViewById<Button>(R.id.scanBtn)
        val connectBtn = findViewById<Button>(R.id.connectBtn)
        val ancSwitch = findViewById<Switch>(R.id.ancSwitch)

        // Fake battery generator
        battery.text = "Battery: ${Random.nextInt(40, 100)}%"

        scanBtn.setOnClickListener {
            Toast.makeText(this, "Scanning for AirPods...", Toast.LENGTH_SHORT).show()
        }

        connectBtn.setOnClickListener {
            connected = true
            status.text = "Connected to AirPods"
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()
        }

        ancSwitch.setOnCheckedChangeListener { _, isChecked ->
            ancEnabled = isChecked
            if (connected) {
                Toast.makeText(
                    this,
                    if (isChecked) "ANC ON (simulated)" else "ANC OFF",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this, "Not connected", Toast.LENGTH_SHORT).show()
                ancSwitch.isChecked = false
            }
        }
    }
}
