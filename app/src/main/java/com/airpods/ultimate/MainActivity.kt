package com.airpods4anc

import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var connected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val status = findViewById<TextView>(R.id.status)
        val battery = findViewById<TextView>(R.id.battery)

        val scanBtn = findViewById<Button>(R.id.scanBtn)
        val connectBtn = findViewById<Button>(R.id.connectBtn)
        val ancSwitch = findViewById<Switch>(R.id.ancSwitch)

        battery.text = "Battery: ${Random.nextInt(40, 100)}%"

        scanBtn.setOnClickListener {
            Toast.makeText(this, "Scan OK", Toast.LENGTH_SHORT).show()
        }

        connectBtn.setOnClickListener {
            connected = true
            status.text = "Connected"
        }

        ancSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (!connected) {
                ancSwitch.isChecked = false
                Toast.makeText(this, "Not connected", Toast.LENGTH_SHORT).show()
                return@setOnCheckedChangeListener
            }

            Toast.makeText(
                this,
                if (isChecked) "ANC ON" else "ANC OFF",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
