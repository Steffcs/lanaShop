package com.example.lanashop.presentation.ui.checkout

import androidx.lifecycle.MutableLiveData
import com.example.lanashop.domain.model.OrdersResponse

import com.example.lanashop.domain.usecase.checkout.GetOrdersUseCase
import com.example.lanashop.presentation.commons.BaseViewModel

class OrdersViewModel constructor(private val getOrdersUseCase: GetOrdersUseCase)
    : BaseViewModel() {

    val ordersData = MutableLiveData<List<OrdersResponse>>()

    fun getUserOrders(){
        getOrdersUseCase.execute(onSuccess = {
            ordersData.value=it
        },onError = {

        })

    }




}

