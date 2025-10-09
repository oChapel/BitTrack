package com.example.bittrack.data.network

import com.example.bittrack.data.network.model.CoinCapAssetResponseDto
import retrofit2.http.GET

interface CoinCapApi {

    @GET("assets/bitcoin")
    suspend fun getBitcoinPrice(): CoinCapAssetResponseDto
}
