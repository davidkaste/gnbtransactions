package com.davidcastella.features.productlist.mappers

import com.davidcastella.domain.transactions.entities.Transaction
import com.davidcastella.features.productlist.models.ProductTransactionsUI
import org.junit.Assert.*

import org.junit.Test
import java.math.BigDecimal

class TransactionListMapperTest {

    private val mapper = TransactionListMapper()

    @Test
    operator fun invoke() {
        val transactionList = listOf(
            Transaction("A", BigDecimal(12.3), "EUR"),
            Transaction("A", BigDecimal(12.3), "EUR"),
            Transaction("B", BigDecimal(12.3), "EUR"),
            Transaction("B", BigDecimal(12.3), "EUR")
        )

        val expectedProductList = listOf(
            ProductTransactionsUI("A", listOf(BigDecimal(12.3), BigDecimal(12.3))),
            ProductTransactionsUI("B", listOf(BigDecimal(12.3), BigDecimal(12.3)))
        )

        val result = mapper(transactionList)

        assertEquals(expectedProductList.count(), result.count())

        assertEquals(expectedProductList[0].productSku, result[0].productSku)
        assertEquals(expectedProductList[0].amountList, result[0].amountList)

        assertEquals(expectedProductList[1].productSku, result[1].productSku)
        assertEquals(expectedProductList[1].amountList, result[1].amountList)
    }
}