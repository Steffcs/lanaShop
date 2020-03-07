package com.example.lanashop.domain.usecase.checkout

import android.annotation.SuppressLint
import com.android.artgallery.domain.usecase.base.SingleUseCase
import com.example.lanashop.domain.model.ProductsResponse
import com.example.lanashop.domain.repository.ProductsRepository
import io.reactivex.Single

class GetProductsUseCase constructor(
    private val productsRepository: ProductsRepository
) :  SingleUseCase<ProductsResponse>() {

    @SuppressLint("CheckResult")
    override fun buildUseCaseSingle(): Single<ProductsResponse> {
        return productsRepository.getProducts()
    }

}