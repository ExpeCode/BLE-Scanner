package com.ble.scanner.repository

import androidx.lifecycle.LiveData
import com.ble.scanner.model.ItemBluetoothDevice

interface BluetoothRepository {
    fun getBluetoothLowEnergyDevices(): LiveData<List<ItemBluetoothDevice>>
    fun isEnabledBluetooth(): Boolean
    fun startScan()
    fun stopScan()
}