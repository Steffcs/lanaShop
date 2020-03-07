package com.example.lanashop

import android.app.Application
import com.example.lanashop.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import androidx.multidex.MultiDex


class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(AppModule)
        }
    }
}