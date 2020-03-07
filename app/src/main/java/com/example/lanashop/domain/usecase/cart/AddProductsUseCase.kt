package com.example.lanashop.domain.usecase.cart

import com.android.artgallery.domain.usecase.base.SingleUseCase
import com.example.lanashop.domain.model.Product
import com.example.lanashop.domain.model.ProductCode
import com.example.lanashop.domain.repository.ProductsRepository
import io.reactivex.Single
import kotlin.math.roundToLong

class AddProductsUseCase constructor(
private val productsRepository: ProductsRepository
) :  SingleUseCase<List<Product>>() {

    private lateinit var mproducts:Product
    private  var voucherCount = 0
    private  var  discountCount = 0L

    fun addProducts(products: Product,action: Int) {

        when(action){
            1 -> products.count++
            0 -> products.count--
        }
        products.total = products.count * products.price
        products.totalWithDiscount = products.total
        getDiscount(products)
    }

     fun getDiscount(product: Product) {

        if ( ProductCode.TSHIRT == product.code && product.count >= 3) {
            product.totalWithDiscount = product.total * 0.95
        } else if (ProductCode.VOUCHER == product.code && product.count >=2) {

            for (i in 0 until product.count) {
                voucherCount++
                if(voucherCount == 2) {
                    discountCount += (product.count / 2.0).roundToLong()
                    product.totalWithDiscount = discountCount * product.price
                    voucherCount = 0;
                    discountCount=0
                }
            }
        }
        productsRepository.updateProduct(product)
    }


    override fun buildUseCaseSingle(): Single<List<Product>> {
        return productsRepository.getLocalProducts()
    }

}