package com.davidcastella.data.api.models

data class ConversionRateResponseModel(
    val from: String,
    val to: String,
    val rate: Double
)
