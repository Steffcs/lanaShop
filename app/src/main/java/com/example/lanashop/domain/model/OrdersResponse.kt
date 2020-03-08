package com.example.lanashop.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "Orders")
data class OrdersResponse(
    @PrimaryKey(autoGenerate = true)
    val orderId : Int,
    val orderDate : String,
    val count : Int,
    val total : Double,
    val totalWithDiscount: Double)


