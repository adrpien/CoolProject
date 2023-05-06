package com.example.coolproject.feature_market.data.mappers

import com.example.coolproject.feature_market.data.remote.dto.IntradayInfoDto
import com.example.coolproject.feature_market.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun IntradayInfoDto.toIntradayInfo(): IntradayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val dateTimeFormatter = LocalDateTime.parse(time, formatter)
    return IntradayInfo(
        time = dateTimeFormatter,
        close = close
    )
}