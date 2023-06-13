package online.transporteari.chambea.library.net.exception

class BadRequestException : ServiceException {
    constructor() : super() {}
    constructor(message: String?) : super(message) {}
    constructor(message: String?, cause: Throwable?) : super(message, cause) {}
    constructor(cause: Throwable?) : super(cause) {}
}
