package com.ble.scanner.repository

import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ble.scanner.model.ItemBluetoothDevice
import com.ble.scanner.utils.arePermissionsGranted
import com.ble.scanner.utils.isPermissionGrantedBluetoothScan

class BluetoothRepositoryImpl(private val context: Context): BluetoothRepository {

    private val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter = bluetoothManager.adapter
    private val devicesLiveData = MutableLiveData<List<ItemBluetoothDevice>>()
    private val scanCallback = object : ScanCallback() {
        private val devicesList = mutableListOf<ItemBluetoothDevice>()
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val device = result.device
            val index = devicesList.indexOfFirst { it.address == device.address }
            if (index == -1) {
                devicesList.add(ItemBluetoothDevice(device.name, device.address))
            } else {
                devicesList[index] = ItemBluetoothDevice(device.name, device.address)
            }
            devicesLiveData.postValue(devicesList.toList())
        }
    }

    override fun isEnabledBluetooth(): Boolean = bluetoothAdapter.isEnabled

    override fun getBluetoothLowEnergyDevices(): LiveData<List<ItemBluetoothDevice>> {
        return devicesLiveData
    }

    override fun startScan() {
        if (arePermissionsGranted(context) && isEnabledBluetooth()) {
            val scanFilters = listOf(
                ScanFilter.Builder()
                    .setDeviceName(null)
                    .setServiceUuid(null)
                    .build()
            )

            val scanSettings = ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .build()

            bluetoothAdapter
                .bluetoothLeScanner
                .startScan(scanFilters, scanSettings, scanCallback)
        }
    }

    override fun stopScan() {
        if (isPermissionGrantedBluetoothScan(context)) {
            bluetoothAdapter
                .bluetoothLeScanner
                .stopScan(scanCallback)
        }
    }

}