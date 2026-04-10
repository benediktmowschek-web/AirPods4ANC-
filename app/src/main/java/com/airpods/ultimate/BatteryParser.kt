package com.airpods4anc

data class BatteryData(
    val left: Int,
    val right: Int,
    val case: Int
)

object BatteryParser {

    fun getBattery(): BatteryData {
        return BatteryData(
            (70..100).random(),
            (70..100).random(),
            (70..100).random()
        )
    }
}
