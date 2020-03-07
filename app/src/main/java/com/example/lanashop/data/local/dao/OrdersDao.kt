package com.example.lanashop.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lanashop.domain.model.OrdersResponse
import com.example.lanashop.domain.model.Product
import io.reactivex.Single

/**
 * it provides access to [Product] underlying database
 * */
@Dao
interface OrdersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: OrdersResponse): Long

    @Query("SELECT * FROM Orders")
    fun loadAll(): Single<List<OrdersResponse>>
}