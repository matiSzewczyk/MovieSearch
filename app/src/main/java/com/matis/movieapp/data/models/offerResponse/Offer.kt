package com.matis.movieapp.data.models.offerResponse

data class Offer(
    val additionalServices: AdditionalServices,
    val afterSalesServices: AfterSalesServices,
    val b2b: B2b,
    val category: Category,
    val delivery: Delivery,
    val `external`: External,
    val id: String,
    val name: String,
    val primaryImage: PrimaryImage,
    val publication: Publication,
    val saleInfo: SaleInfo,
    val sellingMode: SellingMode,
    val stats: Stats,
    val stock: Stock
)