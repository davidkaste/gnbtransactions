package com.davidcastella.domain.conversionrates.util

import com.davidcastella.domain.conversionrates.entities.ConversionRate
import java.math.BigDecimal

fun List<ConversionRate>.getConversionRate(from: String, to: String): BigDecimal {
    val rest = firstOrNull { it.from == from && it.to == to }
    return if (rest != null) rest.rate
    else {
        val n = first { it.from == from }
        n.rate * getConversionRate(n.to, to)
    }
}
