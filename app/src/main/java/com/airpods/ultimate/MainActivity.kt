package com.airpods4anc

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var connected = false
    private var anc = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val status = findViewById<TextView>(R.id.status)
        val battery = findViewById<TextView>(R.id.battery)

        val scanBtn = findViewById<Button>(R.id.scanBtn)
        val connectBtn = findViewById<Button>(R.id.connectBtn)
        val ancSwitch = findViewById<Switch>(R.id.ancSwitch)
        val playPause = findViewById<Button>(R.id.playPause)

        // Initial battery (simuliert, aber stabil)
        battery.text = "Battery: ${Random.nextInt(50, 100)}%"

        scanBtn.setOnClickListener {
            Toast.makeText(this, "Scanning Bluetooth devices...", Toast.LENGTH_SHORT).show()
        }

        connectBtn.setOnClickListener {
            connected = true
            status.text = "Connected to AirPods"
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()
        }

        ancSwitch.setOnCheckedChangeListener { _, isChecked ->
            anc = isChecked

            if (!connected) {
                Toast.makeText(this, "Not connected", Toast.LENGTH_SHORT).show()
                ancSwitch.isChecked = false
                return@setOnCheckedChangeListener
            }

            Toast.makeText(
                this,
                if (isChecked) "ANC ON" else "ANC OFF",
                Toast.LENGTH_SHORT
            ).show()
        }

        playPause.setOnClickListener {
            Toast.makeText(this, "Play/Pause toggled", Toast.LENGTH_SHORT).show()
        }
    }
}
