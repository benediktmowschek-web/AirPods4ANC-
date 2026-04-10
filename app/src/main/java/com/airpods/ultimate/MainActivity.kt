package com.airpods4anc

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var connected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val status = findViewById<TextView>(R.id.status)

        val main = findViewById<TextView>(R.id.mainBattery)
        val left = findViewById<TextView>(R.id.leftBattery)
        val right = findViewById<TextView>(R.id.rightBattery)
        val case = findViewById<TextView>(R.id.caseBattery)

        val connectBtn = findViewById<Button>(R.id.connectBtn)
        val refreshBtn = findViewById<Button>(R.id.refreshBtn)

        fun updateBattery() {
            val l = Random.nextInt(40, 100)
            val r = Random.nextInt(40, 100)
            val c = Random.nextInt(30, 100)

            main.text = "${(l + r) / 2}%"
            left.text = "$l%"
            right.text = "$r%"
            case.text = "$c%"
        }

        connectBtn.setOnClickListener {
            connected = true
            status.text = "Connected to AirPods"
            updateBattery()
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()
        }

        refreshBtn.setOnClickListener {
            if (connected) {
                updateBattery()
            } else {
                Toast.makeText(this, "Not connected", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
