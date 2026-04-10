package com.airpods4anc

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var connected = false
    private lateinit var batteryView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val status = findViewById<TextView>(R.id.status)
        batteryView = findViewById(R.id.battery)

        val connectBtn = findViewById<Button>(R.id.connectBtn)
        val scanBtn = findViewById<Button>(R.id.scanBtn)
        val ancSwitch = findViewById<Switch>(R.id.ancSwitch)

        scanBtn.setOnClickListener {
            Toast.makeText(this, "Scanning...", Toast.LENGTH_SHORT).show()
        }

        connectBtn.setOnClickListener {
            connected = true
            status.text = "Connected"
            updateBattery()
        }

        ancSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (!connected) {
                ancSwitch.isChecked = false
                Toast.makeText(this, "Not connected", Toast.LENGTH_SHORT).show()
                return@setOnCheckedChangeListener
            }

            Toast.makeText(this,
                if (isChecked) "ANC ON" else "ANC OFF",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // ✅ FUNKTION MUSS HIER STEHEN (OUTSIDE onCreate)
    private fun updateBattery() {
        batteryView.text = "${Random.nextInt(50, 100)}%"
    }
}
