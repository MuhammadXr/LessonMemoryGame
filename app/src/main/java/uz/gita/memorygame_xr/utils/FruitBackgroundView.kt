package uz.gita.memorygame_xr.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Point
import android.os.Build
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.internal.managers.ViewComponentManager
import uz.gita.memorygame_xr.R

class FruitBackgroundView(var contextt: Context, attr: AttributeSet) : View(contextt, attr) {
    var screenWidth = 0
    var screenHeight = 0
    var newWidth = 0
    var newHeight = 0

    var bgX = 0f
    var bgY = 0f


    val runnable = Runnable {
        kotlin.run {
            invalidate()
        }
    }


    val handlerr = Handler(context.mainLooper)

    val UPDATE_MILLIS = 40

    var bg: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.fr_bg_1)

    init {

        contextt = if (contextt is ViewComponentManager.FragmentContextWrapper) {
            (contextt as ViewComponentManager.FragmentContextWrapper).baseContext
        } else
            contextt

        val display = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.display
        } else {
            (contextt as Activity).windowManager.defaultDisplay
        }
        val size = Point()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics =
                (contextt as FragmentActivity).windowManager.currentWindowMetrics.bounds
            size.x = windowMetrics.width()
            size.y = windowMetrics.height()
        } else {
            display?.getSize(size)
        }

        screenWidth = size.x
        screenHeight = size.y

        var height = bg.height
        var width = bg.width
        var ratio = width / height

        newHeight = screenHeight
        newWidth = ratio * screenHeight

        bg = Bitmap.createScaledBitmap(bg, newWidth, newHeight, false)


    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        bgX -= 1
        bgY -= 1

        if (bgX < -newWidth) {
            bgX = 0f
        }

        if (bgY < -newHeight) {
            bgY = 0f
        }
        canvas?.drawBitmap(bg, bgX, bgY, null)

        if (bgX < screenWidth - newWidth) {
            canvas?.drawBitmap(bg, (bgX + newWidth).toFloat(), bgY.toFloat(), null)
        }

        if (bgY < screenHeight - newHeight) {
            canvas?.drawBitmap(bg, bgX.toFloat(), (bgY + newHeight).toFloat(), null)
        }
        if (bgY < screenHeight - newHeight || bgX < screenWidth - newWidth) {
            canvas?.drawBitmap(bg, (bgX + newWidth).toFloat(), (bgY + newHeight).toFloat(), null)
        }

        handlerr.postDelayed(runnable, UPDATE_MILLIS.toLong())
    }
}