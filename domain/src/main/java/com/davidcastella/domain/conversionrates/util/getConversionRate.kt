package com.davidcastella.domain.conversionrates.util

import com.davidcastella.domain.conversionrates.entities.ConversionRate
import java.math.BigDecimal

fun List<ConversionRate>.getConversionRate(from: String, to: String): BigDecimal {
    val rest = firstOrNull { it.from == from && it.to == to }
    return rest?.rate
        ?: if (from == to) BigDecimal(1)
        else {
            val n = first { it.from == from }
            n.rate * filter { it != n && it.to != from }.getConversionRate(n.to, to)
        }
}
