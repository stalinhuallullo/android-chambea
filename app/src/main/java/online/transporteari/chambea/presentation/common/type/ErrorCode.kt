package online.transporteari.chambea.presentation.common.type

import androidx.annotation.IntDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy


@Retention(RetentionPolicy.SOURCE)
@IntDef(
    ErrorCode.DEFAULT,
    ErrorCode.NETWORK,
    ErrorCode.SERVICE,
    ErrorCode.SQL,
    ErrorCode.SESSION,
    ErrorCode.HTTP,
    ErrorCode.BAD_REQUEST,
    ErrorCode.UNAUTHORIZED,
    ErrorCode.FORBIDDEN,
    ErrorCode.NOT_FOUND,
    ErrorCode.UPDATE_USER_INFO,
    ErrorCode.UPDATE_APP,
    ErrorCode.INTERNAL_SERVER,
    ErrorCode.NOT_IMPLEMENTED,
    ErrorCode.BAD_GATEWAY,
    ErrorCode.SERVICE_UNAVAILABLE,
    ErrorCode.GATEWAY_TIMEOUT
)
annotation class ErrorCode {
    companion object {
        const val DEFAULT = 0
        const val NETWORK = 1
        const val SERVICE = 2
        const val SQL = 3
        const val SESSION = 4
        const val HTTP = 5
        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
        const val FORBIDDEN = 403
        const val NOT_FOUND = 404
        const val UPDATE_USER_INFO = 417
        const val UPDATE_APP = 424
        const val INTERNAL_SERVER = 500
        const val NOT_IMPLEMENTED = 501
        const val BAD_GATEWAY = 502
        const val SERVICE_UNAVAILABLE = 503
        const val GATEWAY_TIMEOUT = 504
    }
}
