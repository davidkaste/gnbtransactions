package com.davidcastella.domain.conversionrates.util

import com.davidcastella.domain.conversionrates.entities.ConversionRate
import org.junit.Assert.assertEquals

import org.junit.Before
import org.junit.Test

class GetConversionRateKtTest {

    private lateinit var list: List<ConversionRate>

    @Before
    fun setUp() {
        list = listOf(
            ConversionRate("AUD", "EUR", 1.27.toBigDecimal()),
            ConversionRate("EUR", "AUD", 0.79.toBigDecimal()),
            ConversionRate("AUD", "CAD", 1.06.toBigDecimal()),
            ConversionRate("CAD", "AUD", 0.94.toBigDecimal()),
            ConversionRate("EUR", "USD", 0.64.toBigDecimal()),
            ConversionRate("USD", "EUR", 1.56.toBigDecimal())
        )
    }

    @Test
    fun getDirectConversionRate() {
        val rate = list.getConversionRate("EUR", "USD")
        val rate2 = list.getConversionRate("CAD", "AUD")

        assertEquals(0.64.toBigDecimal(), rate)
        assertEquals(0.94.toBigDecimal(), rate2)
    }

    @Test
    fun getIndirectConversionRate() {
        val rate = list.getConversionRate("CAD", "EUR")
        val rate2 = list.getConversionRate("USD", "CAD")

        assertEquals(1.1938.toBigDecimal(), rate)
        assertEquals(1.306344.toBigDecimal(), rate2)
    }
}
