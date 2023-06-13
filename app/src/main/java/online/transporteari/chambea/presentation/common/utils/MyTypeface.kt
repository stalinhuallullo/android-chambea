package online.transporteari.chambea.presentation.common.utils

import android.content.Context
import android.graphics.Typeface

class MyTypeface {
    private var context: Context

    constructor(context: Context) {
        this.context = context
    }

    fun openMultiBold(): Typeface {
        return Typeface.createFromAsset(context.assets, "fonts/Muli-Bold.ttf")
    }

    fun openMultiRegular(): Typeface {
        return Typeface.createFromAsset(context.assets, "fonts/Muli-Regular.ttf")
    }
}