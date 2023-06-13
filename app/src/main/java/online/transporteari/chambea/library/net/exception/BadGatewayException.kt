package online.transporteari.chambea.library.net.exception

class BadGatewayException : ServiceException {
    constructor() : super() {}
    constructor(message: String?) : super(message) {}
    constructor(message: String?, cause: Throwable?) : super(message, cause) {}
    constructor(cause: Throwable?) : super(cause) {}

    companion object {
        private const val TAG = "BadGatewayException"
    }
}
