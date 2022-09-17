package com.matis.movieapp.data.models.offerResponse

data class OfferResponse(
    val count: Int,
    val offers: List<Offer>,
    val totalCount: Int
)