package com.example.lanashop.data.remote


import com.example.lanashop.domain.model.ProductsResponse
import io.reactivex.Single
import retrofit2.http.GET

interface RemoteServicesApi {

   @GET("/bins/4bwec")
   fun getProductsList(): Single<ProductsResponse>
}