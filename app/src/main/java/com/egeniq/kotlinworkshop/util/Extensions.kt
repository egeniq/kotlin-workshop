package com.egeniq.kotlinworkshop.util;

import android.animation.Animator
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver

fun View.circularReveal(startX: Float, startY: Float) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            val cx = width.toDouble()
            val cy = height.toDouble()
            val finalRadius: Double = Math.hypot(cx, cy)
            val revealAnimation: Animator = ViewAnimationUtils.createCircularReveal(this@circularReveal, startX.toInt(), startY.toInt(), 0f, finalRadius.toFloat())
            revealAnimation.duration = 780
            revealAnimation.start()

            viewTreeObserver.removeOnGlobalLayoutListener(this)
        }
    })
    invalidate()
}