package com.airpods.ultimate

import android.bluetooth.BluetoothAdapter

object BluetoothManager {

    fun getAirPods() =
        BluetoothAdapter.getDefaultAdapter()
            ?.bondedDevices
            ?.firstOrNull { it.name.contains("AirPods", true) }
}
