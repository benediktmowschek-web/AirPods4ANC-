package com.airpods.ultimate

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BluetoothReceiver(
    private val onUpdate: (String, Boolean) -> Unit
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)

        if (device?.name?.contains("AirPods", true) == true) {

            when (intent.action) {

                BluetoothDevice.ACTION_ACL_CONNECTED -> {
                    onUpdate(device.name ?: "AirPods", true)
                }

                BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                    onUpdate("Nicht verbunden", false)
                }
            }
        }
    }
}
