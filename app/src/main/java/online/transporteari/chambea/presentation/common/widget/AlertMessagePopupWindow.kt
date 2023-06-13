package online.transporteari.chambea.presentation.common.widget

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.PopupWindow
import online.transporteari.chambea.R
import online.transporteari.chambea.presentation.common.utils.StringUtil

class AlertMessagePopupWindow() : PopupWindow() {

    private var ivwIcon: ImageView? = null
    private var tvwTitle: CustomTextView? = null
    private var tvwMessage: CustomTextView? = null
    private var btnCancel: CustomTextView? = null
    private var btnOk: CustomButton? = null
    private var pickerContainerV: View? = null
    private var contentView //root view
            : View? = null

    private var mContext: Context? = null
    private var textTitle: String? = null
    private var textMessage: CharSequence? = null
    private var textButton: String? = null
    private var textButton2: String? = null
    private var resIcon = 0
    private var cancellable = false

    var listenerPositive: View.OnClickListener? = null
    var listenerNegative: View.OnClickListener? = null

    class Builder(  //Required
        val context: Context
    ) {
        var listenerPositive: View.OnClickListener? = null
        var listenerNegative: View.OnClickListener? = null
        var textTitle = "Alerta"
        var textMessage: CharSequence = "Ocurrio un error"
        var textButton = "Aceptar"
        var textButton2 = "Cancelar"
        var cancellable = true
        var resIcon = 0
        fun build(): AlertMessagePopupWindow {
            return AlertMessagePopupWindow(this)
        }


        fun setTitle(title: String): Builder {
            textTitle = title
            return this
        }

        fun setMessage(message: CharSequence): Builder {
            textMessage = message
            return this
        }

        fun setTextButton(textButton: String): Builder {
            this.textButton = textButton
            return this
        }

        fun setTextButton2(textButton2: String): Builder {
            this.textButton2 = textButton2
            return this
        }

        fun setOnClickPositive(listenerPositive: View.OnClickListener?): Builder {
            this.listenerPositive = listenerPositive
            return this
        }

        fun setOnClickNegative(listenerNegative: View.OnClickListener?): Builder {
            this.listenerNegative = listenerNegative
            return this
        }

        fun setCancellable(state: Boolean): Builder {
            cancellable = state
            return this
        }

        fun setResIcon(resIcon: Int): Builder {
            this.resIcon = resIcon
            return this
        }
    }

    constructor(builder: Builder) : this() {
        resIcon = builder.resIcon
        textTitle = builder.textTitle
        textMessage = builder.textMessage
        textButton = builder.textButton
        textButton2 = builder.textButton2
        cancellable = builder.cancellable
        listenerPositive = builder.listenerPositive
        listenerNegative = builder.listenerNegative
        mContext = builder.context
        initView()
    }


    /*private fun AlertMessagePopupWindow(builder: Builder) {
        resIcon = builder.resIcon
        textTitle = builder.textTitle
        textMessage = builder.textMessage
        textButton = builder.textButton
        textButton2 = builder.textButton2
        cancellable = builder.cancellable
        listenerPositive = builder.listenerPositive
        listenerNegative = builder.listenerNegative
        mContext = builder.context
        initView()
    }*/

    private fun initView() {
        contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_alert_message, null)
        ivwIcon = contentView?.findViewById(R.id.ivwIcon)
        tvwTitle = contentView?.findViewById(R.id.tvwTitle)
        tvwMessage = contentView?.findViewById(R.id.tvwMessage)
        btnOk = contentView?.findViewById(R.id.btnOk)
        btnCancel = contentView?.findViewById(R.id.btnCancel)
        pickerContainerV = contentView?.findViewById(R.id.lltContainerPicker)
        btnCancel?.setOnClickListener { onClickCancel(contentView!!)}
        btnOk?.setOnClickListener { onClickOk(contentView!!)}
        contentView?.setOnClickListener { onClickContentViewAndCancellable() }
        if (resIcon != 0) {
            ivwIcon?.setImageResource(resIcon)
        }
        if (!StringUtil.isNullOrEmpty(textTitle)) {
            tvwTitle?.setText(textTitle)
        } else {
            tvwTitle?.setVisibility(View.GONE)
        }
        if (!StringUtil.isNullOrEmpty(textMessage)) {
            tvwMessage?.setText(textMessage)
        }
        if (!StringUtil.isNullOrEmpty(textButton)) {
            btnOk?.setText(textButton)
        }
        if (!StringUtil.isNullOrEmpty(textButton2)) {
            btnCancel?.setText(textButton2)
        } else {
            btnCancel?.setVisibility(View.GONE)
        }
        isTouchable = true
        isFocusable = true
        animationStyle = R.style.FadeInPopWin
        setContentView(contentView)
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.MATCH_PARENT
    }

    /**
     * Show popWindow
     *
     * @param activity Activity Context
     */
    fun showPopWin(activity: Activity?) {
        if (null != activity) {
            Log.e("SHOOOOOO", contentView.toString())
            Handler().post {
                showAtLocation(contentView, Gravity.BOTTOM, 0, 0)
                val trans = TranslateAnimation(
                    Animation.RELATIVE_TO_SELF,
                    0f,
                    Animation.RELATIVE_TO_SELF,
                    0f,
                    Animation.RELATIVE_TO_SELF,
                    1f,
                    Animation.RELATIVE_TO_SELF,
                    0f
                )
                trans.duration = 400
                trans.interpolator = AccelerateDecelerateInterpolator()
                pickerContainerV!!.startAnimation(trans)
            }
        }
    }

    /**
     * Show popWindow
     *
     */
    fun showPopWin() {
        Handler().post {
            showAtLocation(contentView, Gravity.BOTTOM, 0, 0)
            val trans = TranslateAnimation(
                Animation.RELATIVE_TO_SELF,
                0f,
                Animation.RELATIVE_TO_SELF,
                0f,
                Animation.RELATIVE_TO_SELF,
                1f,
                Animation.RELATIVE_TO_SELF,
                0f
            )
            trans.duration = 400
            trans.interpolator = AccelerateDecelerateInterpolator()
            pickerContainerV!!.startAnimation(trans)
        }
    }

    /**
     * Dismiss date picker popWindow
     */
    private fun dismissPopWin() {
        val trans = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f
        )
        trans.duration = 400
        trans.interpolator = AccelerateInterpolator()
        trans.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                // EMPTY
            }

            override fun onAnimationRepeat(animation: Animation) {
                // EMPTY
            }

            override fun onAnimationEnd(animation: Animation) {
                dismiss()
            }
        })
        pickerContainerV!!.startAnimation(trans)
    }

    fun onClickContentViewAndCancellable(){
        dismissPopWin()
    }
    fun onClickOk(view: View){
        if (null != listenerPositive) {
            listenerPositive!!.onClick(view)
        }
        dismissPopWin()
    }
    fun onClickCancel(view: View) {
        if (null != listenerNegative) {
            listenerNegative!!.onClick(view)
        }
        dismissPopWin()
    }

}
