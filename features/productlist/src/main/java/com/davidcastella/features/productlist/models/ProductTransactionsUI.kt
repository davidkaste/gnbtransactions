package com.davidcastella.features.productlist.models

import java.math.BigDecimal

data class ProductTransactionsUI(
    val productSku: String,
    val amountList: List<BigDecimal>
)