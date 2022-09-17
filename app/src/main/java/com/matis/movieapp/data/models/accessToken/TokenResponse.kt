package com.matis.movieapp.data.models.accessToken

data class TokenResponse(
    val access_token: String,
    val allegro_api: Boolean,
    val expires_in: Int,
    val jti: String,
    val scope: String,
    val token_type: String
)