package com.example.coolproject.feature_market.domain.model

import java.time.LocalDateTime

data class IntradayInfo(
    val time: LocalDateTime,
    val close: Double
)
