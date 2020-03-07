package com.example.lanashop.presentation.ui.cart

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.lanashop.domain.model.OrdersResponse
import com.example.lanashop.domain.model.Product
import com.example.lanashop.domain.usecase.cart.*
import com.example.lanashop.domain.usecase.checkout.GetProductsUseCase
import com.example.lanashop.presentation.commons.BaseViewModel
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class ProductsViewModel constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getLocalProductsUseCase: GetLocalProductsUseCase, private val
    addProductsUseCase: AddProductsUseCase,
    private  val getDiscountUseCase: GetDiscountUseCase,
    private val deleteProductsUse: DeleteProductsUse,
    private val addOrderUseCase: AddOrderUseCase
) : BaseViewModel() {

    private val TAG = ProductsViewModel::class.java.name
    val productsData = MutableLiveData<List<Product>>()
    val showProgressbar = MutableLiveData<Boolean>()
    val deleteCart = MutableLiveData<Boolean>()
    val messageData = MutableLiveData<String>()
    val total = MutableLiveData<Double>()
    var count = 0.0

    fun getCheckout(){
        count=total.value!!
        if(total.value!!>0.0){
            showProgressbar.value=true
            var date = Date()
            val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
            val answer: String = formatter.format(date)

            addOrderUseCase.adOrders(OrdersResponse(0, answer, productsData.value?.sumBy{ it.count++}!!,productsData.value?.sumByDouble { it.total++}!!,
                productsData.value?.sumByDouble { it.totalWithDiscount++}!!
            ))
            addOrderUseCase.execute(onSuccess = {
                deleteCart()

            },onError = {
                showProgressbar.value=false
            })
        }
    }


    fun deleteCart(){
        deleteProductsUse.execute(onError = {},onSuccess = {
            deleteCart.value=true
            getProducts()
        })
    }


    fun getProducts() {
        deleteCart.value=false
        showProgressbar.value = true

        getProductsUseCase.execute(
            onSuccess = {
                refreshCart(it.products)
        },
            onError = {
                it.printStackTrace()
                messageData.value = "check your internet connection"

            })
    }

    fun refreshCart(products: List<Product>?) {
        getLocalProductsUseCase.checkProducts(products)
        getLocalProductsUseCase.execute(
            onError = {
                it.printStackTrace()
                messageData.value = "ups something went wrong "

            },
            onSuccess = {
                Log.i(TAG,"result: $it")
                productsData.value= it
                })
    }


    fun updateProduct(product: Product,action: Int){

            addProductsUseCase.addProducts(product, action)
            addProductsUseCase.execute(
                onSuccess = {
                    productsData.value = it
                    getDiscount()
                }
                , onError = {
                    messageData.value = "ups something went wrong "
                })

    }

    fun  getDiscount(){
        getDiscountUseCase.execute(onSuccess = {
            total.value=it
        },onError = {
            messageData.value = "ups something went wrong "
        })
    }





}