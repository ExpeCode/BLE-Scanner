package com.ble.scanner.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

private val requiredPermissions = arrayOf(
    Manifest.permission.BLUETOOTH,
    Manifest.permission.BLUETOOTH_ADMIN,
    Manifest.permission.ACCESS_FINE_LOCATION
)
@RequiresApi(Build.VERSION_CODES.S)
private val requiredPermissionsApi31 = arrayOf(
    Manifest.permission.BLUETOOTH_SCAN,
    Manifest.permission.BLUETOOTH_CONNECT
)

fun getPermissionsList(): Array<String> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
    requiredPermissions + requiredPermissionsApi31
} else {
    requiredPermissions
}

fun arePermissionsGranted(context: Context): Boolean = getPermissionsList().all { permission ->
    ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
}

fun isPermissionGrantedBluetoothScan(context: Context): Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
    ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED
} else {
    true
}
fun isPermissionGrantedBluetoothConnect(context: Context): Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
    ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED
} else {
    true
}