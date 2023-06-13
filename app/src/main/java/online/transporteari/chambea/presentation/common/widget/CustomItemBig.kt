package online.transporteari.chambea.presentation.common.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import online.transporteari.chambea.R
import online.transporteari.chambea.databinding.ViewWidgetItemBigBinding
import online.transporteari.chambea.presentation.common.utils.OnSingleClickListener


class CustomItemBig constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : RelativeLayout(context, attrs) {

    private var binding: ViewWidgetItemBigBinding = ViewWidgetItemBigBinding.inflate(LayoutInflater.from(context))

    var title: String? = null
    var itemText: String? = null
    var subItemText: String? = null
    var enabledButton: Boolean = true
    var itemIcon: Int = 0
    var isDetail: Boolean = false
    var buttonGo: TextView? = null
    var listener: ItemListener? = null
    var tag: String? = null

    init {
        attrs?.let { setupAttrs(context, it) }
        binding.event = this
        setupUI()
        addView(binding.root)
    }

    private fun setupAttrs(context: Context, attrs: AttributeSet) {
        val type = context.obtainStyledAttributes(attrs, R.styleable.CustomItemBig, 0, 0)

        this.setOnClickListener {
        }

        try {

            tag = type.getString(R.styleable.CustomItemBig_tagItem)
            enabledButton = type.getBoolean(R.styleable.CustomItem_enableButton, true)
            isDetail = type.getBoolean(R.styleable.CustomItemBig_isDetail, false)
            itemIcon = type.getResourceId(R.styleable.CustomItemBig_iconDown, R.drawable.furgon_camio)
            title = type.getString(R.styleable.CustomItemBig_itemTitle)
            itemText = type.getString(R.styleable.CustomItemBig_itemText)
            subItemText = type.getString(R.styleable.CustomItemBig_subItemText)

            binding.txtGo.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(v: View) {
                    listener!!.testClick(tag)
                }

            })
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

    fun setTextItem(text: String?) {
        binding.txtItem.text = text
    }

    fun setTextSubItem(text: String?) {
        binding.txtSubItem.text = text
    }

    fun setVisibleDetail(value: Boolean) {
        binding.clBody.visibility = if (value) View.VISIBLE else View.GONE
    }

    fun setTextButtonAdd(text: String?) {
        binding.txtGo.text = text
    }

    fun setOnclickItem(listener: ItemListener) {
        this.listener = listener
    }

    /* fun onClicked(view: View) {
          listener!!.testClick(tag)
      }*/

    fun setTextItemColor(id: Int) {
        binding.txtItem.setTextColor(id)
    }

    fun setTextSubItemColor(id: Int) {
        binding.txtSubItem.setTextColor(id)
    }

    fun setDrawableResource(drawable: Drawable?) {
        binding.imgItem.setImageDrawable(drawable)
    }

    fun setIcon(url: String) {
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.ic_visa)
            .error(R.drawable.ic_visa)
            .into(binding.imgItem);

    }

    fun setVisibleStatus(value: Boolean) {
        binding.txtStatus.visibility = if (value) View.VISIBLE else View.GONE
    }

    private fun setup() {
        buttonGo = binding.txtGo
        binding.txtTitle.text = title
        binding.txtItem.text = itemText
        binding.txtSubItem.text = subItemText
        enabledComponentes(enabledButton)
        setVisibleDetail(isDetail)
        if (tag.isNullOrEmpty()) {
            tag = ""
        }
    }


    fun enabledComponentes(value: Boolean) {
        binding.flItem.isEnabled = value
        enabledButton=value
        setAlpha()
    }
    fun setAlpha(){
        if (!enabledButton) {
            binding.txtTitle.alpha = 0.3f
            binding.txtGo.alpha= 0.3f

        } else {
            binding.txtTitle.alpha = 1f
            binding.txtGo.alpha= 1f

        }
    }
    interface ItemListener {
        fun testClick(tag: String?)
    }

}