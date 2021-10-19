package com.davidcastella.domain.core.util

import com.davidcastella.domain.conversionrates.entities.CurrencyCode
import org.junit.Assert.*

import org.junit.Test
import java.math.BigDecimal

class ToCurrencyStringKtTest {

    @Test
    fun toCurrencyString() {
        val v1 = BigDecimal(34.5).toCurrencyString(CurrencyCode.EUR)
        assertEquals("34,50\u00A0€", v1)

        val v2 = BigDecimal(34.5).toCurrencyString(CurrencyCode.USD)
        assertEquals("34,50\u00A0US$", v2)
    }
}