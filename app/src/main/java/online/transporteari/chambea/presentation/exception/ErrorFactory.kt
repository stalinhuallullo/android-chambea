package online.transporteari.chambea.presentation.exception

import online.transporteari.chambea.data.exception.SessionException
import online.transporteari.chambea.library.net.exception.*
import online.transporteari.chambea.presentation.common.error.ErrorModel
import online.transporteari.chambea.presentation.common.type.ErrorCode
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

object ErrorFactory {
    fun create(exception: Throwable): ErrorModel {
        return if (exception is NetworkConnectionException) {
            ErrorModel(
                ErrorCode.NETWORK, "No hay conexión a internet.",
                (exception).response
            )
        } else if (exception is UpdateVersionException) {
            ErrorModel(ErrorCode.UPDATE_APP, "Es necesario actualizar la aplicación.", null)
        } else if (exception is SqlException) {
            ErrorModel(ErrorCode.SQL, "Ocurrió un error al ejecutar la operación.", null)
        } else if (exception is BadRequestException) {
            ErrorModel(
                ErrorCode.BAD_REQUEST, "Ocurrió un error al ejecutar la operación.",
                (exception).response
            )
        } else if (exception is UnauthorizedException) {
            ErrorModel(
                ErrorCode.UNAUTHORIZED, "Su sesión ha expirado.",
                (exception as UnauthorizedException).response
            )
        } else if (exception is ForbiddenException) {
            ErrorModel(
                ErrorCode.FORBIDDEN, "No tiene permisos necesarios.",
                (exception as ForbiddenException).response
            )
        } else if (exception is NotFoundException) {
            ErrorModel(
                ErrorCode.NOT_FOUND, "No se ha encontrado el servicio.",
                (exception as NotFoundException).response
            )
        } else if (exception is InternalServerErrorException) {
            ErrorModel(
                ErrorCode.INTERNAL_SERVER, "Ocurrió un error al ejecutar la operación.",
                (exception as InternalServerErrorException).response
            )
        } else if (exception is NotImplementedException) {
            ErrorModel(
                ErrorCode.NOT_IMPLEMENTED, "Ocurrió un error al ejecutar la operación.",
                (exception as NotImplementedException).response
            )
        } else if (exception is BadGatewayException) {
            ErrorModel(
                ErrorCode.BAD_GATEWAY, "Ocurrió un error al ejecutar la operación.",
                (exception as BadGatewayException).response
            )
        } else if (exception is ServiceUnavaiableException) {
            ErrorModel(
                ErrorCode.SERVICE_UNAVAILABLE, "Los servicios no estan disponibles.",
                (exception as ServiceUnavaiableException).response
            )
        } else if (exception is GatewayTimeoutException) {
            ErrorModel(
                ErrorCode.GATEWAY_TIMEOUT, "Se superó el tiempo límite de consulta",
                (exception as GatewayTimeoutException).response
            )
        } else if (exception is SessionException) {
            ErrorModel(ErrorCode.SESSION, "Su sesión ha espirado", null)
        } else if (exception is HttpException) {
            ErrorModel(ErrorCode.HTTP, getMessage(exception.code()), null)
        } else if (exception is SSLException) {
            ErrorModel(ErrorCode.HTTP, "Ocurrió un error al ejecutar la operación.", null)
        } else if (exception is UnknownHostException) {
            ErrorModel(ErrorCode.HTTP, "No hay conexión a internet.", null)
        } else if (exception is ConnectException) {
            ErrorModel(ErrorCode.DEFAULT, "Ocurrió un error al ejecutar la operación.", null)
        } else if (exception is UpdateUserInfoException) {
            ErrorModel(
                ErrorCode.UPDATE_USER_INFO,
                "Es necesario completar sus datos para continuar.",
                null
            )
        } else if (exception is ServiceException) {
            if (exception.cause is SocketTimeoutException) {
                ErrorModel(
                    ErrorCode.GATEWAY_TIMEOUT,
                    "Se superó el tiempo límite de consulta",
                    null
                )
            } else {
                ErrorModel(ErrorCode.SERVICE, "Ocurrió un error al ejecutar la operación.", null)
            }
        } else {
            ErrorModel(ErrorCode.DEFAULT, "Ocurrió un error al ejecutar la operación.", null)
        }
    }

    private fun getMessage(code: Int): String {
        return when (code) {
            401 -> "Su sesión ha expirado."
            403 -> "No tiene permisos necesarios."
            404 -> "No se ha encontrado el servicio."
            424 -> "Es necesario actualizar la aplicación."
            503 -> "Los servicios no están disponibles."
            504 -> "Se superó el tiempo límite de consulta"
            400, 500, 501, 502 -> "Ocurrió un error al ejecutar la operación."
            else -> "Ocurrió un error al ejecutar la operación."
        }
    }
}
