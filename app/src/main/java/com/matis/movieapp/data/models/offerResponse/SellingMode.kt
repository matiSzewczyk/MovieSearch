package com.matis.movieapp.data.models.offerResponse

data class SellingMode(
    val format: String,
    val minimalPrice: MinimalPrice,
    val price: Price,
    val startingPrice: StartingPrice
)