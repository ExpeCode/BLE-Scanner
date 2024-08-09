package com.ble.scanner.screens.main

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ble.scanner.R
import com.ble.scanner.adapters.BluetoothDevicesAdapter
import com.ble.scanner.databinding.ActivityMainBinding
import com.ble.scanner.utils.arePermissionsGranted
import com.ble.scanner.utils.getPermissionsList
import com.ble.scanner.utils.isPermissionGrantedBluetoothConnect
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val iRequestEnableBluetoothResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            setupBluetooth()
        }
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private val adapter = BluetoothDevicesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (arePermissionsGranted(this)) {
            if (viewModel.isEnabledBluetooth()) {
                setupBluetooth()
            } else {
                turnOnBluetooth()
            }
        } else {
            requestPermissions(getPermissionsList(), PERMISSIONS_REQUEST_CODE)
        }
    }

    private fun setupBluetooth() {
        binding.rvDeviceList.adapter = adapter

        viewModel.bluetoothDevices.observe(this) { devices ->
            adapter.submitList(devices)
        }

        viewModel.startScan()
    }

    private fun turnOnBluetooth() {
        if (isPermissionGrantedBluetoothConnect(this)) {
            val iRequestEnableBluetooth = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            iRequestEnableBluetoothResultLauncher.launch(iRequestEnableBluetooth)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                if (viewModel.isEnabledBluetooth()) {
                    setupBluetooth()
                } else {
                    turnOnBluetooth()
                }
            } else {
                Toast.makeText(this, R.string.permissions_request_description, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stopScan()
    }

    companion object {
        private const val PERMISSIONS_REQUEST_CODE = 1
    }
}