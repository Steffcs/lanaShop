package com.example.lanashop

import com.example.lanashop.data.remote.RemoteServicesApi
import com.example.lanashop.domain.model.Product
import com.example.lanashop.domain.model.ProductsResponse
import com.example.lanashop.domain.repository.ProductsRepository
import com.example.lanashop.domain.usecase.checkout.GetProductsUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class GetProductsUseCaseTest : KoinTest {

    val useCase: GetProductsUseCase by inject()
    val apiService: RemoteServicesApi by inject()
    val productsRepository: ProductsRepository by inject()

    @Before
    fun setUp() {


        startKoin {
            modules(
                module {
                    single { mock<RemoteServicesApi>() }
                    single {
                        GetProductsUseCase(
                            get()
                        )
                    }
                    single { mock<ProductsRepository>() }

                }
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }


    @Test
    fun `get all Products Remote`() {

        val productsMock: ProductsResponse = ProductsResponse(
            listOf(
                Product("VOUCHER", "Cabify Voucher", 5.0),
                Product("TSHIRT", "Cabify T-Shirt", 20.0),
                Product("MUG", "Cabify Coffee Mug", 7.5)
            )
        )

        whenever(apiService.getProductsList()).thenReturn(Single.just(productsMock))
        whenever(productsRepository.getProducts()).thenReturn(Single.just(productsMock))

        assert(useCase.buildUseCaseSingle().blockingGet().products!![0].code=="VOUCHER")




    }






}