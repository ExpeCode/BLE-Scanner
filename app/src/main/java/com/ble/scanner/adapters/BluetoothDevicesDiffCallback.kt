package com.ble.scanner.adapters

import androidx.recyclerview.widget.DiffUtil
import com.ble.scanner.model.ItemBluetoothDevice

class BluetoothDevicesDiffCallback : DiffUtil.ItemCallback<ItemBluetoothDevice>() {

    override fun areItemsTheSame(oldItem: ItemBluetoothDevice, newItem: ItemBluetoothDevice): Boolean {
        return oldItem.address == newItem.address
    }

    override fun areContentsTheSame(oldItem: ItemBluetoothDevice, newItem: ItemBluetoothDevice): Boolean {
        return oldItem.name == newItem.name
    }
}
