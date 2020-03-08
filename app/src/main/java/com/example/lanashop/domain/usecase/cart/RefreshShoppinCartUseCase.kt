package com.example.lanashop.domain.usecase.cart

import android.annotation.SuppressLint
import com.android.artgallery.domain.usecase.base.SingleUseCase
import com.example.lanashop.domain.model.Product
import com.example.lanashop.domain.repository.ProductsRepository
import io.reactivex.Single

class RefreshShoppinCartUseCase constructor(
    private val productsRepository: ProductsRepository
) :  SingleUseCase<List<Product>>() {

    @SuppressLint("CheckResult")
    override fun buildUseCaseSingle(): Single<List<Product>> {

        return productsRepository.getLocalProducts()
    }
}