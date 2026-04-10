package com.airpods4anc

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice

object BluetoothManager {

    fun getAirPods(): BluetoothDevice? {
        val adapter = BluetoothAdapter.getDefaultAdapter() ?: return null

        return adapter.bondedDevices.firstOrNull {
            it.name.contains("AirPods", true)
        }
    }
}
