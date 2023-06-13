package online.transporteari.chambea.presentation.common.utils

import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.TranslateAnimation

class ConstantFunctions {

    fun animationView(): Animation? {
        val trans = TranslateAnimation(
            Animation.START_ON_FIRST_FRAME, 0f, Animation.START_ON_FIRST_FRAME,
            0f, Animation.START_ON_FIRST_FRAME, 1f,
            Animation.START_ON_FIRST_FRAME, 0f
        )
        trans.duration = 400
        trans.interpolator = AccelerateDecelerateInterpolator()
        return trans
    }
}