package com.example.agrovida.models

import java.time.LocalDateTime

data class SensorReading(
    val timestamp: LocalDateTime,
    val temperature: Float,
    val isActive: Boolean
)

data class TimeRange(
    val start: LocalDateTime,
    val end: LocalDateTime
)
