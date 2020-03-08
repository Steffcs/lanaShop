package com.example.lanashop.domain.usecase.cart

import com.android.artgallery.domain.usecase.base.SingleUseCase
import com.example.lanashop.domain.model.OrdersResponse
import com.example.lanashop.domain.repository.ProductsRepository
import io.reactivex.Single


class AddOrderUseCase constructor(
    private val productsRepository: ProductsRepository
) :  SingleUseCase<Long>() {

    private lateinit var mOrdersResponse: OrdersResponse

    fun adOrders(ordersResponse: OrdersResponse) {
        mOrdersResponse=ordersResponse
    }

    override fun buildUseCaseSingle(): Single<Long> {
        return Single.just(productsRepository.addOrders(mOrdersResponse))
    }

}