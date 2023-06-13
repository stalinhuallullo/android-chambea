package online.transporteari.chambea.presentation.common.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import online.transporteari.chambea.R
import online.transporteari.chambea.presentation.common.utils.MyTypeface

class CustomButton : AppCompatButton {
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attributeSet: AttributeSet?) {
        val myTypeface = MyTypeface(context)
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CustomButton)
        when (typedArray.getInteger(R.styleable.CustomButton_typeFontButton, 0)) {
            1 -> this.setTypeface(myTypeface.openMultiBold())
            else -> this.setTypeface(myTypeface.openMultiRegular())
        }
        typedArray.recycle()
    }
}
