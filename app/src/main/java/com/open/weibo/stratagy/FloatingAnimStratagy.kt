package com.open.weibo.stratagy

import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup

class FloatingAnimStratagy {

    companion object {
        const val ANIMATION_DURATION = 300L
    }

    private var targetView: View? = null
    private var needFadeAnimation: Boolean = true

    private val fadeInAnimation by lazy {
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.addUpdateListener(fadeListener)
        animator.duration = ANIMATION_DURATION
        animator
    }

    private val fadeOffAnimation by lazy {
        val animator = ValueAnimator.ofFloat(1f, 0f)
        animator.addUpdateListener(fadeListener)
        animator.duration = ANIMATION_DURATION
        animator
    }

    private val fadeListener = ValueAnimator.AnimatorUpdateListener { animation ->
        if (animation != null) {
            val alpha = animation.animatedValue as Float? ?: 0f
            targetView?.alpha = alpha

            if (!needFadeAnimation && alpha == 0f) {
                targetView?.visibility = View.GONE
            } else if (needFadeAnimation && alpha == 0f) {
                targetView?.visibility = View.VISIBLE
            }
        }
    }

    fun setTargetView(view: View?) {
        this.targetView = view
    }

    fun fadeIn() {
        if (needFadeAnimation) {
            return
        }
        needFadeAnimation = true
        if (fadeOffAnimation.isRunning) {
            fadeOffAnimation.cancel()
        }
        fadeInAnimation.start()
    }

    fun fadeOff() {
        if (!needFadeAnimation) {
            return
        }
        needFadeAnimation = false
        if (fadeInAnimation.isRunning) {
            fadeInAnimation.cancel()
        }
        fadeOffAnimation.start()
    }
}