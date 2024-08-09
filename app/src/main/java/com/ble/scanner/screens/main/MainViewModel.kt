package com.ble.scanner.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ble.scanner.repository.BluetoothRepository
import com.ble.scanner.model.ItemBluetoothDevice

class MainViewModel(private val repository: BluetoothRepository) : ViewModel() {

    val bluetoothDevices: LiveData<List<ItemBluetoothDevice>> = repository.getBluetoothLowEnergyDevices()

    fun isEnabledBluetooth(): Boolean = repository.isEnabledBluetooth()

    fun startScan() {
        repository.startScan()
    }

    fun stopScan() {
        repository.stopScan()
    }

}