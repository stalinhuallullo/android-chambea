package online.transporteari.chambea.presentation.common.widget

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import online.transporteari.chambea.R
import online.transporteari.chambea.databinding.ViewWidgetComboItemBinding
import online.transporteari.chambea.presentation.common.utils.MyTypeface

class CustomComboItem constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {

    private var binding: ViewWidgetComboItemBinding =
        ViewWidgetComboItemBinding.inflate(LayoutInflater.from(context))

    var fontOut: Typeface? = null
    var title: String? = null
    var enabledButton: Boolean = true
    var arrowIcomVisible: Boolean = true
    var iconRigthVisible: Boolean = false
    var iconRigth: Int = 0
    var titleColor: Int = 0

    init {
        attrs?.let { setupAttrs(context, it) }
        setupUI()
        addView(binding.root)
    }

    private fun setupAttrs(context: Context, attrs: AttributeSet) {
        val type = context.obtainStyledAttributes(attrs, R.styleable.CustomItem, 0, 0)
        val myTypeface = MyTypeface(context)
        this.setOnClickListener {

        }
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                val font = type.getFont(R.styleable.CustomItem_titleFont)
                fontOut = if (font != null) font else myTypeface.openMultiBold()
            } else {
                fontOut = myTypeface.openMultiBold()
            }
            iconRigthVisible = type.getBoolean(R.styleable.CustomItem_iconRigthVisible, false)
            enabledButton = type.getBoolean(R.styleable.CustomItem_enableButton, true)
            arrowIcomVisible = type.getBoolean(R.styleable.CustomItem_arrowVisible, true)

            titleColor = type.getInt(R.styleable.CustomItem_titleColor, getColor(R.color.edt_default_text))
            iconRigth = type.getResourceId(R.styleable.CustomItem_iconRigth, R.drawable.ic_calendar_rc_white)
            title = type.getString(R.styleable.CustomItem_textTitle)

        } finally {
            type.recycle()
        }
    }

    private fun getColor(@ColorRes color: Int): Int {
        return ContextCompat.getColor(context, color)
    }

    private fun setupUI() {
        setup()
    }


    fun setIconVisible(value: Boolean) {
        iconRigthVisible = value
        binding.imgRigth.isVisible = iconRigthVisible
    }

    fun setArrowVisible(value: Boolean) {
        arrowIcomVisible = value
        binding.imgArrow.isVisible = arrowIcomVisible
    }

    fun setInputTitle(title: String?) {
        this.title = title
        binding.txtTitle.text = title
    }

    fun setInputEnabled(enabled: Boolean) {
        enabledButton = enabled
        binding.flItem.isEnabled = enabledButton
        setAlpha()
    }

    private fun setup() {
        binding.txtTitle.setTypeface(fontOut)
        binding.txtTitle.text = title
        setAlpha()
        enabledComponentes()
    }

    fun enabledComponentes() {
        binding.flItem.isEnabled = enabledButton
        binding.imgArrow.isVisible = arrowIcomVisible
        binding.imgRigth.setBackgroundResource(iconRigth)
        binding.imgRigth.isVisible = iconRigthVisible
        binding.txtTitle.setTextColor(titleColor)
        setAlpha()


    }
    fun setAlpha(){
        if (!enabledButton) {
            binding.txtTitle.alpha = 0.3f
            binding.imgArrow.alpha = 0.3f
        } else {
            binding.txtTitle.alpha = 1f
            binding.imgArrow.alpha = 1f
        }
    }

}