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

    private val airpodsKeywords = listOf("AirPods", "AirPods Pro", "AirPods Max")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val status = findViewById<TextView>(R.id.status)
        val deviceList = findViewById<TextView>(R.id.deviceList)
        val battery = findViewById<TextView>(R.id.battery)

        val scanBtn = findViewById<Button>(R.id.scanBtn)
        val connectBtn = findViewById<Button>(R.id.connectBtn)
        val ancSwitch = findViewById<Switch>(R.id.ancSwitch)
        val play = findViewById<Button>(R.id.play)
        val pause = findViewById<Button>(R.id.pause)

        battery.text = "Battery: ${Random.nextInt(50, 100)}%"

        scanBtn.setOnClickListener {
            deviceList.text = "Found:\n- AirPods Pro (simulated)\n- JBL Headphones"
            Toast.makeText(this, "Scan complete", Toast.LENGTH_SHORT).show()
        }

        connectBtn.setOnClickListener {
            connected = true
            status.text = "Status: Connected to AirPods"
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()
        }

        ancSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (!connected) {
                ancSwitch.isChecked = false
                Toast.makeText(this, "Not connected", Toast.LENGTH_SHORT).show()
                return@setOnCheckedChangeListener
            }

            ancEnabled = isChecked
            Toast.makeText(
                this,
                if (isChecked) "ANC Enabled" else "ANC Disabled",
                Toast.LENGTH_SHORT
            ).show()
        }

        play.setOnClickListener {
            Toast.makeText(this, "Play", Toast.LENGTH_SHORT).show()
        }

        pause.setOnClickListener {
            Toast.makeText(this, "Pause", Toast.LENGTH_SHORT).show()
        }
    }
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
