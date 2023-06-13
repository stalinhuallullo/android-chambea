package online.transporteari.chambea.presentation.common.utils

import java.text.ParseException
import java.text.SimpleDateFormat

class DateUtils {

    fun getDayName(text: String): String? {
        val parseador = SimpleDateFormat("yyyy-MM-dd", Constant().LOCALE_MX)
        val formateador = SimpleDateFormat("EEEE", Constant().LOCALE_MX)
        try {

            val actualDate = parseador.parse(text)
            return formateador.format(actualDate)
        } catch (e: ParseException) {
        }
        return null
    }
}