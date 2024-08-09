package com.ble.scanner.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ble.scanner.R
import com.ble.scanner.databinding.ItemBluetoothDeviceBinding
import com.ble.scanner.model.ItemBluetoothDevice

class BluetoothDevicesAdapter : ListAdapter<ItemBluetoothDevice, BluetoothDevicesAdapter.ItemBluetoothDeviceViewHolder>(BluetoothDevicesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemBluetoothDeviceViewHolder {
        val view = ItemBluetoothDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemBluetoothDeviceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemBluetoothDeviceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemBluetoothDeviceViewHolder(private val bindingItem: ItemBluetoothDeviceBinding) : RecyclerView.ViewHolder(bindingItem.root) {
        fun bind(device: ItemBluetoothDevice) {
            bindingItem.tvDeviceName.text = device.name ?: bindingItem.root.context.getString(R.string.unknown_device)
            bindingItem.tvDeviceAddress.text = device.address ?: ""
        }
    }
}