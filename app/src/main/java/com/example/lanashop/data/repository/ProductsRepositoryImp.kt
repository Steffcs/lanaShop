package com.example.lanashop.data.repository

import com.example.lanashop.data.local.AppDatabase
import com.example.lanashop.domain.model.ProductsResponse
import com.example.lanashop.data.remote.RemoteServicesApi
import com.example.lanashop.domain.model.OrdersResponse
import com.example.lanashop.domain.model.Product
import com.example.lanashop.domain.repository.ProductsRepository
import io.reactivex.Single

class ProductsRepositoryImp(private val remoteServicesApi: RemoteServicesApi, private val database: AppDatabase) : ProductsRepository {

    override  fun getProducts(): Single<ProductsResponse> {
        return remoteServicesApi.getProductsList()
    }

    override  fun getLocalProducts():Single<List<Product>> {
       return database.productDao.loadAll()
    }

    override  fun addProduct(product: Product) {
        database.productDao.insert(product)
    }

    override  fun deleteProduct(product: Product) {
        database.productDao.delete(product)
    }

    override fun deleteProducts() {
        database.productDao.deleteAll()
    }

    override  fun updateProduct(product: Product) {
       return database.productDao.update(product)
    }

    override fun getProductById(code: String): Int? {
        return database.productDao.getProductByCode(code)
    }

    override fun getTotal(): Single<Double> {
        return database.productDao.getTotal()
    }

    override fun getDiscount(): Single<Double> {
        return database.productDao.getDiscount()
    }

    override fun getOrders():Single<List<OrdersResponse>> {
       return database.ordersDao.loadAll()
    }

    override fun addOrders(ordersResponse: OrdersResponse): Long {
        return  database.ordersDao.insert(ordersResponse)
    }


}