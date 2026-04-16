package com.airpods.ultimate

data class BatteryData(
    val left: Int,
    val right: Int,
    val case: Int
)

object BatteryHelper {

    fun getFallback(): BatteryData {
        return BatteryData(
            (80..100).random(),
            (80..100).random(),
            (80..100).random()
        )
    }
}
