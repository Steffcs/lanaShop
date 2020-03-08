package com.example.lanashop.domain.usecase.checkout

import com.android.artgallery.domain.usecase.base.SingleUseCase
import com.example.lanashop.domain.model.OrdersResponse
import com.example.lanashop.domain.repository.ProductsRepository
import io.reactivex.Single

class GetOrdersUseCase constructor(
    private val productsRepository: ProductsRepository
) :  SingleUseCase<List<OrdersResponse>>() {


    override fun buildUseCaseSingle(): Single<List<OrdersResponse>> {
        return productsRepository.getOrders()
    }

}