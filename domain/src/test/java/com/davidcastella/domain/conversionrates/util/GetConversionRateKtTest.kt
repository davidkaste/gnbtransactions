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
            ConversionRate(from="EUR", to="USD", rate=1.04.toBigDecimal()),
            ConversionRate(from="USD", to="EUR", rate=0.96.toBigDecimal()),
            ConversionRate(from="GBP", to="EUR", rate=1.16.toBigDecimal()),
            ConversionRate(from="JPY", to="USD", rate=0.0073.toBigDecimal()),
            ConversionRate(from="AUD", to="INR", rate=55.29.toBigDecimal()),
            ConversionRate(from="CAD", to="USD", rate=0.74.toBigDecimal()),
            ConversionRate(from="SEK", to="USD", rate=0.096.toBigDecimal()),
            ConversionRate(from="RUB", to="SEK", rate=0.17.toBigDecimal()),
            ConversionRate(from="INR", to="EUR", rate=0.012.toBigDecimal())
        )
    }

    @Test
    fun getDirectConversionRate() {
        val rate = list.getConversionRate("USD", "EUR")
        val rate2 = list.getConversionRate("CAD", "USD")

        assertEquals(0.96.toBigDecimal(), rate)
        assertEquals(0.74.toBigDecimal(), rate2)
    }

    @Test
    fun getIndirectConversionRate1() {
        val rate = list.getConversionRate("CAD", "EUR")

        assertEquals(0.7104.toBigDecimal(), rate)
    }

    @Test
    fun getIndirectConversionRate2() {
        val rate2 = list.getConversionRate("RUB", "EUR")

        assertEquals(0.0156672.toBigDecimal(), rate2)
    }

    @Test
    fun getIndirectConversionRate3() {
        val rate2 = list.getConversionRate("USD", "USD")

        assertEquals(1.toBigDecimal(), rate2)
    }
}
