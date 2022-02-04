package com.davidcastella.domain.core.util

import com.davidcastella.domain.conversionrates.entities.CurrencyCode
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import java.util.*

class ToCurrencyStringKtTest {

    @Test
    fun toCurrencyString() {
        Locale.setDefault(Locale.GERMANY)

        val v1 = BigDecimal(1234.5).toCurrencyString(CurrencyCode.EUR)
        assertEquals("1.234,50\u00A0€", v1)

        val v2 = BigDecimal(1234.5).toCurrencyString(CurrencyCode.USD)
        assertEquals("1.234,50\u00A0$", v2)
    }
}
