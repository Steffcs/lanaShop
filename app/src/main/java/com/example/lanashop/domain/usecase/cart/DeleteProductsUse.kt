package com.example.lanashop.domain.usecase.cart

import com.android.artgallery.domain.usecase.base.SingleUseCase
import com.example.lanashop.domain.repository.ProductsRepository
import io.reactivex.Single

class DeleteProductsUse constructor(
    private val productsRepository: ProductsRepository
) :  SingleUseCase<Unit>() {

    override fun buildUseCaseSingle(): Single<Unit> {
        return  Single.just(productsRepository.deleteProducts())
    }
}