package com.example.module_net

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.telephony.CellSignalStrength
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NetworkSignalActivity : AppCompatActivity() {
    private lateinit var telephonyManager: TelephonyManager
    private lateinit var network_type: android.widget.TextView
    private lateinit var signal_level: android.widget.TextView
    private lateinit var dbm_value: android.widget.TextView

    companion object {
        private val TAG = "NetworkSignalActivity"
        private const val PERMISSION_REQUEST_CODE = 100
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_TELEPHONY_RADIO_ACCESS)) {
            Log.d(TAG, "hasSystemFeature true")
            // 安全地执行需要此功能的操作
            telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        } else {
            Log.d(TAG, "This device does not have cellular radio capabilities.")
            // 例如，隐藏通话相关的UI，或者提示用户
        }

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            0
        )
        updateSignalStrength()
    }
    private fun initView() {
        network_type = findViewById(R.id.network_type)
        signal_level = findViewById(R.id.signal_level)
        dbm_value = findViewById(R.id.dbm_value)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                updateSignalInfo()
            } else {
                Toast.makeText(this, "权限被拒绝，使用模拟信号", Toast.LENGTH_SHORT).show()
//                updateSimulatedSignal()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateSignalStrength() {
        val allCellInfo = telephonyManager.allCellInfo
        if (allCellInfo != null && allCellInfo.isNotEmpty()) {
            val cellInfo = allCellInfo[0]
            val signalStrength = cellInfo.cellSignalStrength

            val networkType = getNetworkType()
            val signalLevel = when {
                signalStrength.level == CellSignalStrength.SIGNAL_STRENGTH_NONE_OR_UNKNOWN -> 0
                signalStrength.level == CellSignalStrength.SIGNAL_STRENGTH_POOR -> 1
                signalStrength.level == CellSignalStrength.SIGNAL_STRENGTH_MODERATE -> 2
                signalStrength.level == CellSignalStrength.SIGNAL_STRENGTH_GOOD -> 3
                signalStrength.level == CellSignalStrength.SIGNAL_STRENGTH_GREAT -> 4
                else -> 0
            }

            runOnUiThread {
                network_type.text = "网络类型: $networkType"
                signal_level.text = "信号强度: ${"★".repeat(signalLevel)}"
                dbm_value.text = "dBm值: ${signalStrength.dbm} dBm"
            }
        }
    }

    private fun getNetworkType(): String {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork
        val capabilities = cm.getNetworkCapabilities(network)

        return when {
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> {
                when {
                    capabilities.hasCapability(
                        NetworkCapabilities.NET_CAPABILITY_FOREGROUND
                    ) -> "5G"

                    else -> "4G"
                }
            }

            else -> "未知网络"
        }
    }

}