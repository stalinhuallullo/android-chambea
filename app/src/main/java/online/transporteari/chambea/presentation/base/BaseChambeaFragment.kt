package online.transporteari.chambea.presentation.base

import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import online.transporteari.chambea.presentation.base.mvp.LoadingView
import online.transporteari.chambea.presentation.base.mvp.View

abstract class BaseChambeaFragment: Fragment(), LoadingView, View {
    private var baseChambeaActivity: BaseChambeaActivity? = null
    protected var loadingView: LoadingView? = null

    private var mIsInjected = false

    protected fun showAlertMessage(
        @DrawableRes icon: Int,
        title: String?,
        message: CharSequence?,
        textButton: String?,
        textButton2: String?,
        listenerPositive: android.view.View.OnClickListener?,
        listenerNegative: android.view.View.OnClickListener?
    ) {
        if (baseChambeaActivity != null) {
            baseChambeaActivity!!.showAlertMessage(
                icon,
                title,
                message,
                textButton,
                textButton2,
                listenerPositive,
                listenerNegative
            )
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }
}