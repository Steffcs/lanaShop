package com.example.lanashop.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    @SerializedName("products")
    val products: List<Product>?)


class ProductCode {
    companion object {
        const val VOUCHER = "VOUCHER"
        const val TSHIRT = "TSHIRT"
        const val MUG = "MUG"
    }
}


@Entity(tableName = "Product")

data class Product(
    @PrimaryKey val code: String,
    val name: String?,
    val price: Double,
    var count: Int = 0,
    var total: Double = 0.0,
    var totalWithDiscount: Double = 0.0
)