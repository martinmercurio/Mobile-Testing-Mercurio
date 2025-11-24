package com.example.mtapi3

import retrofit2.Call
import retrofit2.http.GET

interface ProductService {

    @GET("b/MXBA")
    fun getProducts(): Call<ProductResponse>
}