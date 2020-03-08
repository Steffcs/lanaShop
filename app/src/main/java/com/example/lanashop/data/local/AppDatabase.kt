package com.example.lanashop.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lanashop.data.local.dao.OrdersDao
import com.example.lanashop.data.local.dao.ProductDao
import com.example.lanashop.domain.model.OrdersResponse
import com.example.lanashop.domain.model.Product

@Database(entities =[Product::class,OrdersResponse::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val productDao: ProductDao

    abstract val ordersDao: OrdersDao

    companion object {
        const val DB_NAME = "ShoppingCart.db"
    }
}
