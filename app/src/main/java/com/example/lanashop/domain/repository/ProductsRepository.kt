package com.example.lanashop.domain.repository

import com.example.lanashop.domain.model.OrdersResponse
import com.example.lanashop.domain.model.Product
import com.example.lanashop.domain.model.ProductsResponse
import io.reactivex.Single

interface ProductsRepository {

    fun getProducts(): Single<ProductsResponse>

    fun getLocalProducts(): Single<List<Product>>

    fun addProduct(product: Product)

    fun deleteProduct(product: Product)

    fun deleteProducts()

    fun updateProduct(product: Product)

    fun getProductById(code: String) : Int?

    fun getTotal() : Single<Double>

    fun getDiscount(): Single<Double>

    fun getOrders(): Single<List<OrdersResponse>>

    fun addOrders(ordersResponse: OrdersResponse): Long
}