package com.example.lanashop

import com.example.lanashop.data.remote.RemoteServicesApi
import com.example.lanashop.domain.model.Product
import com.example.lanashop.domain.repository.ProductsRepository
import com.example.lanashop.domain.usecase.cart.AddProductsUseCase
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


class AddProductUseCaseTest  : KoinTest {

    val useCase: AddProductsUseCase by inject()
    val productsRepository: ProductsRepository by inject()

    @Before
    fun setUp() {

        startKoin {
            modules(
                module {
                    single { mock<RemoteServicesApi>() }
                    single {
                        AddProductsUseCase(
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
    fun `should update a product`() {

        val product: Product = Product("VOUCHER", "Cabify Voucher", 5.0, 0, 0.0, 0.0)

        val list = mutableListOf<Product>()
            list.add(Product("VOUCHER", "Cabify Voucher", 5.0,1,0.0,0.0))
            list.add(Product("TSHIRT", "Cabify T-Shirt", 20.0,0,0.0,0.0))
            list.add(Product("MUG", "Cabify Coffee Mug", 7.5,0,0.0,0.0))


        whenever(productsRepository.getLocalProducts()).thenReturn(Single.just(list))
        useCase.addProducts(product, 1)

        assert(useCase.buildUseCaseSingle().blockingGet()[0].count == 1L)




    }

    @Test
    fun `should apply discount 2 * 1 `() {

        val product: Product = Product("VOUCHER", "Cabify Voucher", 5.0, 3, 0.0, 0.0)

        val list = mutableListOf<Product>()
        list.add(Product("VOUCHER", "Cabify Voucher", 5.0,3,15.0,10.0))
        list.add(Product("TSHIRT", "Cabify T-Shirt", 20.0,0,0.0,0.0))
        list.add(Product("MUG", "Cabify Coffee Mug", 7.5,0,0.0,0.0))


        whenever(productsRepository.getLocalProducts()).thenReturn(Single.just(list))
        useCase.addProducts(product, 1)

        assert(useCase.buildUseCaseSingle().blockingGet()[0].totalWithDiscount == 10.0)



    }


    @Test
    fun `should apply discount more products `() {

        val product: Product = Product("TSHIRT", "Cabify Voucher", 5.0, 3, 0.0, 0.0)

        val list = mutableListOf<Product>()
        list.add(Product("VOUCHER", "Cabify Voucher", 5.0,0,0.0,0.0))
        list.add(Product("TSHIRT", "Cabify T-Shirt", 20.0,3,0.0,57.0))
        list.add(Product("MUG", "Cabify Coffee Mug", 7.5,0,0.0,0.0))


        whenever(productsRepository.getLocalProducts()).thenReturn(Single.just(list))
        useCase.addProducts(product, 1)

        assert(useCase.buildUseCaseSingle().blockingGet()[1].totalWithDiscount == 57.0)



    }
}