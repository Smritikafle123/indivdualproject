package com.smriti.musicalinstrumentapp.response

import com.smriti.musicalinstrumentapp.entity.Product

data class ProductResponse(
        val success: Boolean? = null,
        val products: List<Product>? = null
)
