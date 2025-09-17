package com.example.module_net

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class SignalStrengthView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // 网络类型：4G 或 5G
    var networkType: String = "4G"
        set(value) {
            field = value
            invalidate() // 重绘视图
        }
    
    // 信号强度 (0-5)
    var signalStrength: Int = 3
        set(value) {
            field = value.coerceIn(0, 5) // 限制在0-5范围内
            invalidate() // 重绘视图
        }
    
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 36f
        textAlign = Paint.Align.CENTER
    }
    
    private val signalPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.FILL
    }
    
    private val noSignalPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.argb(0x40, 0xFF, 0xFF, 0xFF) // #40FFFFFF
        style = Paint.Style.FILL
    }
    
    // 信号条参数
    private val barWidth = 12f
    private val barSpacing = 4f
    private val barHeights = arrayOf(12f, 18f, 24f, 30f, 36f)
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        val centerX = width / 2f
        val textY = 40f
        
        // 绘制网络类型文本
        canvas.drawText(networkType, centerX, textY, textPaint)
        
        // 绘制信号条
        val totalBarsWidth = 5 * barWidth + 4 * barSpacing
        val startX = centerX - totalBarsWidth / 2
        
        for (i in 0 until 5) {
            val barLeft = startX + i * (barWidth + barSpacing)
            val barTop = textY + 20f + (barHeights[4] - barHeights[i])
            val barRight = barLeft + barWidth
            val barBottom = textY + 20f + barHeights[4]
            
            if (i < signalStrength) {
                // 有信号的条
                canvas.drawRect(barLeft, barTop, barRight, barBottom, signalPaint)
            } else {
                // 无信号的条（半透明）
                canvas.drawRect(barLeft, barTop, barRight, barBottom, noSignalPaint)
            }
        }
    }
    
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // 设置视图的默认大小
        val desiredWidth = 120
        val desiredHeight = 120
        
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        
        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> Math.min(desiredWidth, widthSize)
            else -> desiredWidth
        }
        
        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> Math.min(desiredHeight, heightSize)
            else -> desiredHeight
        }
        
        setMeasuredDimension(width, height)
    }
}