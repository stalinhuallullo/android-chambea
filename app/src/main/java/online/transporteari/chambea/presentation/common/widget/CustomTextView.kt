package online.transporteari.chambea.presentation.common.widget

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import online.transporteari.chambea.R
import online.transporteari.chambea.presentation.common.utils.MyTypeface


class CustomTextView : AppCompatTextView {
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    private fun init(context: Context, attributeSet: AttributeSet?) {
        includeFontPadding = false
        val myTypeface = MyTypeface(context)
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CustomTextView)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) when (typedArray.getInteger(
            R.styleable.CustomTextView_typeFontText,
            0
        )) {
            1 -> this.setTypeface(myTypeface.openMultiBold())
            else -> this.setTypeface(myTypeface.openMultiRegular())
        }
        typedArray.recycle()
    }
}
