package com.example.lanashop.domain.usecase.cart

import com.android.artgallery.domain.usecase.base.SingleUseCase
import com.example.lanashop.domain.model.Product
import com.example.lanashop.domain.repository.ProductsRepository
import io.reactivex.Single

class GetLocalProductsUseCase constructor(
private val productsRepository: ProductsRepository
) :  SingleUseCase<List<Product>>() {

    private lateinit var mproducts:List<Product>

    fun checkProducts(products: List<Product>?) {
        mproducts = products!!
    }

    override fun buildUseCaseSingle(): Single<List<Product>> {
        mproducts.forEach {
            val id = productsRepository.getProductById(it.code)
            if(id ==0){
                productsRepository.addProduct(it)
            }
        }
        return productsRepository.getLocalProducts()
    }

}