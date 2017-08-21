package org.stormphoenix.uicomponent

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet

/**
 * Created by StormPhoenix on 17-8-21.
 * StormPhoenix is a intelligent Android developer.
 *
 * 用来显示闪现动画的 ImageView
 */

class FlashImageView : AppCompatImageView {

    private val mFlashPaint: Paint
    private val mMaskPaint: Paint

    private var velocity: Int = 100;
    private var length: Int = 0
    private var angle: Double = Math.PI / 4

    // 第一条闪现条纹的宽度
    var mFirstFlashLineWidth: Int = 250
    // 第二条闪现条纹的宽度
    var mSecondFlashLineWidth: Int = 50
    // 两条闪现条纹之间的宽度
    var mLinesInterval: Int = 100

    init {
        mFlashPaint = Paint()
        mFlashPaint.color = Color.WHITE

        mMaskPaint = Paint()
//        mMaskPaint.color = Color.GREEN
        mMaskPaint.color = Color.rgb(98, 185, 0)
//        mMaskPaint.color = Color.WHITE
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        length = (w / Math.tan(angle)).toInt() / 2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawFlash(canvas)
        length += velocity
        if (length < height + width / Math.tan(angle)
                + (mFirstFlashLineWidth
                + mSecondFlashLineWidth
                + mLinesInterval) / Math.sin(angle)) {
            postInvalidate()
        }
    }

    fun drawFlash(canvas: Canvas): Unit {
        for (delta in 0..mFirstFlashLineWidth) {
            canvas.drawLine(width.toFloat(), length.toFloat() - delta, 0F, (length - delta - width / Math.tan(angle)).toFloat(), mFlashPaint)
        }

        for (delta in 0..mSecondFlashLineWidth) {
            canvas.drawLine(width.toFloat(), length.toFloat() - mFirstFlashLineWidth - mLinesInterval - delta, 0F, (length.toFloat() - mFirstFlashLineWidth - mLinesInterval - delta - width / Math.tan(angle)).toFloat(), mFlashPaint)
        }

        for (delta in 1..(height + width / Math.tan(angle)).toInt() + mFirstFlashLineWidth + 100) {
            canvas.drawLine(width.toFloat(), length.toFloat() + delta, 0F, (length + delta - width / Math.tan(angle)).toFloat(), mMaskPaint)
        }
    }
}
