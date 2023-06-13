package online.transporteari.chambea.presentation.base

import android.app.AlertDialog
import android.util.Log
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import online.transporteari.chambea.presentation.common.widget.AlertMessagePopupWindow

abstract class BaseChambeaActivity : AppCompatActivity() {
    var alertMessagePopupWindow: AlertMessagePopupWindow? = null
    private var alertDialog: AlertDialog? = null


    /**********************************************************/

    open fun showAlertMessage(
        @DrawableRes icon: Int, title: String?, message: CharSequence?,
        textButton: String?, textButton2: String?,
        listenerPositive: View.OnClickListener?, listenerNegative: View.OnClickListener?
    ) {
        showAlertMessage(
            icon,
            title,
            message,
            textButton,
            textButton2,
            listenerPositive,
            listenerNegative,
            true
        )
    }

    open fun showAlertMessage(
        @DrawableRes icon: Int, title: String?, message: CharSequence?,
        textButton: String?, textButton2: String?,
        listenerPositive: View.OnClickListener?, listenerNegative: View.OnClickListener?,
        cancellable: Boolean
    ) {
        if (alertMessagePopupWindow != null && alertMessagePopupWindow!!.isShowing()) {
            return
        }
        try {
            alertMessagePopupWindow = AlertMessagePopupWindow.Builder(this)
                .setResIcon(icon)
                .setTitle(title.toString())
                .setMessage(message.toString())
                .setTextButton(textButton.toString())
                .setTextButton2(textButton2.toString())
                .setOnClickPositive(listenerPositive)
                .setOnClickNegative(listenerNegative)
                .setCancellable(cancellable)
                .build()

            alertMessagePopupWindow!!.showPopWin(this)
        } catch (e: IllegalStateException) {
            Log.e("AlertMessagePopupWindow.showPopWin", "ERROR: ${e.message}}")
        }
    }

}