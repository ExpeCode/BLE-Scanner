package com.ble.scanner.di

import com.ble.scanner.repository.BluetoothRepository
import com.ble.scanner.repository.BluetoothRepositoryImpl
import com.ble.scanner.screens.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MainViewModel)
    singleOf(::BluetoothRepositoryImpl) { bind<BluetoothRepository>() }
}