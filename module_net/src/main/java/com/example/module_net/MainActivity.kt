package com.example.module_net

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.CellSignalStrength
import android.telephony.PhoneStateListener
import android.telephony.SignalStrength
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    private lateinit var signalView: SignalStrengthView
    private lateinit var tvNetworkInfo: TextView
    private lateinit var tvNetworkStrength: TextView
    private lateinit var btnRefresh: Button

    private var phoneStateListener: MyPhoneStateListener? = null
    private lateinit var telephonyManager: TelephonyManager

    // 用于演示的模拟信号强度
    private var latedStrength = 0
    private var latedNetworkType = "0G"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // 初始化视图
        initViews()

        // 获取TelephonyManager
        telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        checkSignalPermissions()
        checkSignalStrengthPermission()

        // 设置按钮点击监听器
        setupClickListeners()
    }

    private fun initViews() {
        signalView = findViewById(R.id.signalView)
        tvNetworkInfo = findViewById(R.id.tvNetworkInfo)
        tvNetworkStrength = findViewById(R.id.tvNetworkStrength)
        btnRefresh = findViewById(R.id.btnRefresh)
    }

    /**
     * 刷新信号
     */
    private fun setupClickListeners() {
        btnRefresh.setOnClickListener {
            checkSignalPermissions()
            checkSignalStrengthPermission()
        }
    }
    private fun checkSignalPermissions() {
        // 检查权限
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_BASIC_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "Requesting phone state permissions")
            // 请求权限
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_BASIC_PHONE_STATE
                ),
                PERMISSION_REQUEST_CODE
            )
        } else {
            // 已有权限，更新信号
            Log.d(TAG, "Requesting phone has permissions")
            updateSignalInfo()
            setupPhoneStateListener()
        }
    }
    private fun setupPhoneStateListener() {
        if (phoneStateListener == null) {
            phoneStateListener = MyPhoneStateListener()

            // 注册监听器，监听信号强度和通话状态变化
            telephonyManager.listen(
                phoneStateListener,
                PhoneStateListener.LISTEN_SIGNAL_STRENGTHS or
                        PhoneStateListener.LISTEN_DATA_CONNECTION_STATE or
                        PhoneStateListener.LISTEN_SERVICE_STATE
            )
        }
    }
    private fun checkSignalStrengthPermission(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission_group.LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "Requesting phone state permissions")
            // 请求权限
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSION_REQUEST_CODE2
            )
        } else {
            // 已有权限，更新信号
            Log.d(TAG, "Requesting phone has permissions2")
            updateSignalStrength()
        }
    }
    @SuppressLint("MissingPermission")
    private fun updateSignalInfo() {
        // 获取网络类型
        val networkType = getNetworkTypeName()
        // 更新UI
        signalView.networkType = networkType

        Log.d(TAG, "updateSignalInfo networkType $networkType")
        tvNetworkInfo.text = "网络类型: $networkType"
    }

    private fun updateSignalStrength() {
        val signalStrength = getSignalStrength() // 这里简化处理，实际应该从SignalStrength获取
        Log.d(TAG, "updateSignalInfo signalStrength $signalStrength")
        signalView.signalStrength = signalStrength
        tvNetworkStrength.text = "信号强度: $signalStrength/5"
    }

    @SuppressLint("MissingPermission")
    private fun getSignalStrength(): Int {
        var signalLevel = 0
        val allCellInfo = telephonyManager.allCellInfo
        if (allCellInfo != null && allCellInfo.isNotEmpty()) {
            val cellInfo = allCellInfo[0]
            val signalStrength = cellInfo.cellSignalStrength

            signalLevel = when {
                signalStrength.level == CellSignalStrength.SIGNAL_STRENGTH_NONE_OR_UNKNOWN -> 0
                signalStrength.level == CellSignalStrength.SIGNAL_STRENGTH_POOR -> 1
                signalStrength.level == CellSignalStrength.SIGNAL_STRENGTH_MODERATE -> 2
                signalStrength.level == CellSignalStrength.SIGNAL_STRENGTH_GOOD -> 3
                signalStrength.level == CellSignalStrength.SIGNAL_STRENGTH_GREAT -> 4
                else -> 0
            }
        }
        return signalLevel
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED ||
                        grantResults.getOrElse(1) { PackageManager.PERMISSION_DENIED } == PackageManager.PERMISSION_GRANTED)
            ) {
                updateSignalInfo()
            } else {
                Log.d(TAG, "updateSignalInfo permission denied")
                Toast.makeText(this, "权限被拒绝，使用模拟信号", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE2) {
            if (grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED ||
                        grantResults.getOrElse(1) { PackageManager.PERMISSION_DENIED } == PackageManager.PERMISSION_GRANTED)
            ) {
                updateSignalStrength()
            } else {
                Log.d(TAG, "updateSignalStrength permission denied")
                Toast.makeText(this, "权限被拒绝，使用模拟信号", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        phoneStateListener?.let {
            telephonyManager.listen(it, PhoneStateListener.LISTEN_NONE)
        }
    }
    /**
     * PhoneStateListener
     */
    inner class MyPhoneStateListener : PhoneStateListener() {

        override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
            super.onSignalStrengthsChanged(signalStrength)
            Log.d(TAG, "onSignalStrengthsChanged")
            // 获取信号强度值 (dBm)
           val dbmValue = if (signalStrength.cellSignalStrengths.isNotEmpty()) {
                // 使用第一个可用的信号强度值
                signalStrength.cellSignalStrengths[0].dbm
                Log.d(TAG, "onSignalStrengthsChanged dbm ${signalStrength.cellSignalStrengths[0].dbm}")
            } else {
                // 回退到传统方法
                if (signalStrength.gsmSignalStrength != 99) {
                    // GSM信号强度转换为dBm
                    -113 + 2 * signalStrength.gsmSignalStrength
                    Log.d(TAG, "gsmSignalStrength ")
                } else {
                    // 尝试获取LTE信号强度
                    try {
                        val method = signalStrength.javaClass.getMethod("getLteDbm")
                        method.invoke(signalStrength) as Int
                        Log.d(TAG, "getLteDbm")
                    } catch (e: Exception) {
                        -1 // 未知
                        Log.d(TAG, "getLteDbm err ${e.cause}")
                    }
                }
            }
            latedStrength = convertSignalStrengthToBars(dbmValue)
            tvNetworkStrength.text = "信号强度2: $latedStrength/5"
            updateSignalInfo()
        }

        override fun onDataConnectionStateChanged(state: Int, networkType: Int) {
            super.onDataConnectionStateChanged(state, networkType)
            Log.d(TAG, "onDataConnectionStateChanged $state networkType $networkType")
            // 网络连接状态变化时更新网络类型
            updateSignalInfoBack()
        }

        override fun onServiceStateChanged(serviceState: android.telephony.ServiceState?) {
            super.onServiceStateChanged(serviceState)
            Log.d(TAG, "onServiceStateChanged $serviceState")
            // 服务状态变化时更新网络信息
            updateSignalInfoBack()
        }
    }
    private fun updateSignalInfoBack() {
        // 获取网络类型
        latedNetworkType = getNetworkTypeName()
        // 更新UI
        signalView.networkType = latedNetworkType
        tvNetworkInfo.text = "网络类型: $latedNetworkType"
    }

    @SuppressLint("MissingPermission")
    private fun getNetworkTypeName(): String {
        return when (telephonyManager.dataNetworkType) {
            TelephonyManager.NETWORK_TYPE_GPRS -> "2G"
            TelephonyManager.NETWORK_TYPE_EDGE -> "2G"
            TelephonyManager.NETWORK_TYPE_CDMA -> "2G"
            TelephonyManager.NETWORK_TYPE_1xRTT -> "2G"
            TelephonyManager.NETWORK_TYPE_IDEN -> "2G"
            TelephonyManager.NETWORK_TYPE_UMTS -> "3G"
            TelephonyManager.NETWORK_TYPE_EVDO_0 -> "3G"
            TelephonyManager.NETWORK_TYPE_EVDO_A -> "3G"
            TelephonyManager.NETWORK_TYPE_HSDPA -> "3G"
            TelephonyManager.NETWORK_TYPE_HSUPA -> "3G"
            TelephonyManager.NETWORK_TYPE_HSPA -> "3G"
            TelephonyManager.NETWORK_TYPE_EVDO_B -> "3G"
            TelephonyManager.NETWORK_TYPE_EHRPD -> "3G"
            TelephonyManager.NETWORK_TYPE_HSPAP -> "3G"
            TelephonyManager.NETWORK_TYPE_LTE -> "4G"
            TelephonyManager.NETWORK_TYPE_NR -> "5G"
            else -> "未知"
        }
    }

    private fun convertSignalStrengthToBars(signalStrength: Int): Int {
        // 将dBm信号强度转换为0-5的条形图值
        // 这是一个简化的转换，实际转换可能因设备和网络类型而异
        return when {
            signalStrength >= -85 -> 5 // 极强信号
            signalStrength >= -95 -> 4 // 强信号
            signalStrength >= -105 -> 3 // 中等信号
            signalStrength >= -115 -> 2 // 弱信号
            signalStrength >= -125 -> 1 // 极弱信号
            else -> 0 // 无信号
        }
    }
    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
        private const val PERMISSION_REQUEST_CODE2 = 200
        private const val TAG = "MainActivity"
    }
}