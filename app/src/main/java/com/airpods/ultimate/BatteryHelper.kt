package com.airpods.ultimate

data class BatteryData(
    val left: Int,
    val right: Int,
    val case: Int
)

object BatteryHelper {

    fun getSmartBattery(real: Int?): BatteryData {

        if (real != null && real > 0) {
            return BatteryData(
                real,
                (real - (0..5).random()).coerceAtLeast(0),
                (real + (0..3).random()).coerceAtMost(100)
            )
        }

        // realistischer Fake
        val base = (85..100).random()

        return BatteryData(
            base,
            base - (0..3).random(),
            base + (0..2).random()
        )
    }
}
