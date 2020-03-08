package com.example.lanashop.di

import android.app.Application
import androidx.room.Room
import com.example.lanashop.BuildConfig
import com.example.lanashop.data.local.AppDatabase
import com.example.lanashop.data.local.dao.OrdersDao
import com.example.lanashop.data.local.dao.ProductDao
import com.example.lanashop.data.remote.RemoteServicesApi
import com.example.lanashop.data.repository.ProductsRepositoryImp

import com.example.lanashop.domain.repository.ProductsRepository
import com.example.lanashop.domain.usecase.cart.*
import com.example.lanashop.domain.usecase.checkout.GetOrdersUseCase
import com.example.lanashop.domain.usecase.checkout.GetProductsUseCase
import com.example.lanashop.presentation.ui.cart.ProductsViewModel
import com.example.lanashop.presentation.ui.checkout.OrdersViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * dependency injection
 * */
val AppModule = module {

    single { createService(get()) }

    single { createRetrofit(get(), BuildConfig.API_URL,get(),get()) }

    single { createOkHttpClient() }

    single { createGsonConverterFactory() }

    single { createRxJava2CallAdapterFactory() }

    viewModel {OrdersViewModel(
        get()
    )}

    viewModel {

        ProductsViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

    single { createDatabase(get()) }

    single { provideProductDao(get()) }

    single { provideOrdersDao(get()) }

    single { createProductsRepository(get(),createDatabase(get())) }

    single { createGetProductsUseCase(get()) }

    single { createGetDiscountUseCase(get()) }

    single { createGetLocalProductsUseCase(get()) }

    single { createAddProductsUseCase(get()) }

    single { createRefreshShoppinCartUseCase(get()) }

    single { createAddOrderUseCase(get()) }

    single { createGetOrderUseCase(get()) }

    single { createDeleteProductsUse(get()) }

}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}

fun createRetrofit(okHttpClient: OkHttpClient, url: String,gsonConverterFactory: GsonConverterFactory,rxJava2CallAdapterFactory: RxJava2CallAdapterFactory): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .addCallAdapterFactory(rxJava2CallAdapterFactory)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory).build()
}


fun createGsonConverterFactory(): GsonConverterFactory {
    return GsonConverterFactory.create()
}
fun createRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory {
    return RxJava2CallAdapterFactory.create()
}


fun createService(retrofit: Retrofit): RemoteServicesApi {
    return retrofit.create(RemoteServicesApi::class.java)
}

fun createDatabase(application: Application): AppDatabase {
    return Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        AppDatabase.DB_NAME
    ).allowMainThreadQueries().build()
}


 fun provideProductDao(appDatabase: AppDatabase): ProductDao {
    return appDatabase.productDao
}

fun provideOrdersDao(appDatabase: AppDatabase): OrdersDao {
    return appDatabase.ordersDao
}

fun createProductsRepository(remoteServicesApi: RemoteServicesApi, appDatabase: AppDatabase): ProductsRepository {
    return ProductsRepositoryImp(remoteServicesApi,appDatabase)
}


fun createGetProductsUseCase(
    productsRepository: ProductsRepository
): GetProductsUseCase {
    return GetProductsUseCase(
        productsRepository
    )
}

fun createAddProductsUseCase(
    productsRepository: ProductsRepository
): AddProductsUseCase {
    return AddProductsUseCase(
        productsRepository
    )
}

fun createGetLocalProductsUseCase(
    productsRepository: ProductsRepository
): GetLocalProductsUseCase {
    return GetLocalProductsUseCase(
        productsRepository
    )
}

fun createRefreshShoppinCartUseCase(
    productsRepository: ProductsRepository
): RefreshShoppinCartUseCase {
    return RefreshShoppinCartUseCase(
        productsRepository
    )
}

fun createGetDiscountUseCase(
    productsRepository: ProductsRepository
): GetDiscountUseCase {
    return GetDiscountUseCase(
        productsRepository
    )
}


fun createAddOrderUseCase(
    productsRepository: ProductsRepository
): AddOrderUseCase {
    return AddOrderUseCase(
        productsRepository
    )
}

fun createGetOrderUseCase(
    productsRepository: ProductsRepository
): GetOrdersUseCase {
    return GetOrdersUseCase(
        productsRepository
    )
}
fun createDeleteProductsUse(
    productsRepository: ProductsRepository
): DeleteProductsUse {
    return DeleteProductsUse(
        productsRepository
    )
}