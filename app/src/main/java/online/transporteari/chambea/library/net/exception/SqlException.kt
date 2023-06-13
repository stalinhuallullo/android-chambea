package online.transporteari.chambea.library.net.exception

import java.io.IOException

class SqlException : IOException {
    constructor() : super() {}
    constructor(message: String?) : super(message) {}
    constructor(message: String?, cause: Throwable?) : super(message, cause) {}
    constructor(cause: Throwable?) : super(cause) {}

    companion object {
        private const val TAG = "SqlException"
    }
}
