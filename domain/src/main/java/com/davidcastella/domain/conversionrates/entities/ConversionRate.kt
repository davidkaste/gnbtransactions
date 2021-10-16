package com.davidcastella.domain.conversionrates.entities

import java.math.BigDecimal

data class ConversionRate(
    val from: String,
    val to: String,
    val rate: BigDecimal
)
