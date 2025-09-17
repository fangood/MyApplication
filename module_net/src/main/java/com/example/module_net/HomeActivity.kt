package com.example.module_net

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.PhoneStateListener
import android.telephony.SignalStrength
import android.telephony.TelephonyManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class HomeActivity : AppCompatActivity() {
    
    private lateinit var signalView: SignalStrengthView
    private lateinit var tvNetworkInfo: TextView
    private lateinit var btnRefresh: Button
    
    private lateinit var telephonyManager: TelephonyManager
    private var phoneStateListener: MyPhoneStateListener? = null
    
    // 用于演示的模拟信号强度
    private var simulatedStrength = 3
    private var simulatedNetworkType = "4G"
    private var useSimulatedData = false
    
    // 存储实际的信号强度值
    private var actualSignalStrength = 0
    private var actualNetworkType = "未知"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // 初始化视图
        initViews()
        
        // 获取TelephonyManager
        telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        
        // 检查权限
        if (checkPermissions()) {
            // 已有权限，设置监听器
            setupPhoneStateListener()
        }
        
        // 设置按钮点击监听器
        setupClickListeners()
    }
    
    private fun initViews() {
        signalView = findViewById(R.id.signalView)
        tvNetworkInfo = findViewById(R.id.tvNetworkInfo)
        btnRefresh = findViewById(R.id.btnRefresh)
    }
    
    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_PHONE_STATE
        ) == PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
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
    
    private fun setupClickListeners() {
        btnRefresh.setOnClickListener {
            if (checkPermissions()) {
                useSimulatedData = false
                updateSignalInfo()
            } else {
                requestPermissions()
            }
        }
    }
    
    private fun updateSignalInfo() {
        // 获取网络类型
        actualNetworkType = getNetworkTypeName()
        
        // 更新UI
        signalView.networkType = actualNetworkType
        signalView.signalStrength = convertSignalStrengthToBars(actualSignalStrength)
        
        tvNetworkInfo.text = "网络类型: $actualNetworkType, 信号强度: ${actualSignalStrength}dBm (${convertSignalStrengthToBars(actualSignalStrength)}/5)"
    }
    
    private fun getNetworkTypeName(): String {
        return if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            when (telephonyManager.dataNetworkType) {
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
        } else {
            "未知(无权限)"
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
    
    private fun updateSimulatedSignal() {
        signalView.networkType = simulatedNetworkType
        signalView.signalStrength = simulatedStrength
        
        tvNetworkInfo.text = "模拟网络: $simulatedNetworkType, 信号强度: $simulatedStrength/5"
    }
    
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            PERMISSION_REQUEST_CODE
        )
    }
    
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                setupPhoneStateListener()
                updateSignalInfo()
            } else {
                Toast.makeText(this, "权限被拒绝，使用模拟信号", Toast.LENGTH_SHORT).show()
                useSimulatedData = true
                updateSimulatedSignal()
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        // 取消监听器
        phoneStateListener?.let {
            telephonyManager.listen(it, PhoneStateListener.LISTEN_NONE)
        }
    }
    
    // 自定义PhoneStateListener
    inner class MyPhoneStateListener : PhoneStateListener() {
        
        override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
            super.onSignalStrengthsChanged(signalStrength)
            
            // 获取信号强度值 (dBm)
            actualSignalStrength = if (signalStrength.cellSignalStrengths.isNotEmpty()) {
                // 使用第一个可用的信号强度值
                signalStrength.cellSignalStrengths[0].dbm
            } else {
                // 回退到传统方法
                if (signalStrength.gsmSignalStrength != 99) {
                    // GSM信号强度转换为dBm
                    -113 + 2 * signalStrength.gsmSignalStrength
                } else {
                    // 尝试获取LTE信号强度
                    try {
                        val method = signalStrength.javaClass.getMethod("getLteDbm")
                        method.invoke(signalStrength) as Int
                    } catch (e: Exception) {
                        -1 // 未知
                    }
                }
            }
            
            if (!useSimulatedData) {
                updateSignalInfo()
            }
        }
        
        override fun onDataConnectionStateChanged(state: Int, networkType: Int) {
            super.onDataConnectionStateChanged(state, networkType)
            
            // 网络连接状态变化时更新网络类型
            if (!useSimulatedData) {
                updateSignalInfo()
            }
        }
        
        override fun onServiceStateChanged(serviceState: android.telephony.ServiceState?) {
            super.onServiceStateChanged(serviceState)
            
            // 服务状态变化时更新网络信息
            if (!useSimulatedData) {
                updateSignalInfo()
            }
        }
    }
    
    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
    }
}