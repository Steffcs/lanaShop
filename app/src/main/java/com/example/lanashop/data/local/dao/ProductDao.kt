package com.example.lanashop.data.local.dao

import androidx.room.*
import com.example.lanashop.domain.model.Product
import io.reactivex.Single

/**
 * it provides access to [Product] underlying database
 * */
@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product): Long

    @Query("SELECT * FROM Product")
    fun loadAll(): Single<List<Product>>

    @Delete
    fun delete(product: Product)

    @Query("DELETE FROM Product")
    fun deleteAll()

    @Query("SELECT COUNT(code) FROM Product where code = :code LIMIT 1")
    fun  getProductByCode(code: String): Int?

    @Query("SELECT SUM(total) FROM Product")
    fun getTotal(): Single<Double>


    @Query("SELECT SUM(totalWithDiscount) FROM Product")
    fun getDiscount(): Single<Double>


    @Update
    fun update(product: Product)

}