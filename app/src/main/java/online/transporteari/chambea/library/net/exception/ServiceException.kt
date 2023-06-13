package online.transporteari.chambea.library.net.exception

import com.google.gson.JsonElement
import java.io.IOException


open class ServiceException : IOException {
    /** */
    var code = 0
    var response: JsonElement? = null

    constructor() : super() {}
    constructor(message: String?) : super(message) {}
    constructor(message: String?, cause: Throwable?) : super(message, cause) {}
    constructor(cause: Throwable?) : super(cause) {}

    companion object {
        private const val TAG = "ServiceException"
    }
}