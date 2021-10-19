package com.davidcastella.domain.core.util

import com.davidcastella.domain.conversionrates.entities.CurrencyCode
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

fun BigDecimal.toCurrencyString(currencyCode: CurrencyCode): String {
    val currencyInstance = Currency.getInstance(currencyCode.toString())
    return NumberFormat.getCurrencyInstance().apply {
        maximumFractionDigits = 2
        currency = currencyInstance
    }.format(this.toDouble())
}