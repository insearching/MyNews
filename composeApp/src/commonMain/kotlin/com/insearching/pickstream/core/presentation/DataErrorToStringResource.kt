package com.insearching.pickstream.core.presentation


import com.insearching.pickstream.core.domain.DataError
import pickstream.composeapp.generated.resources.Res
import pickstream.composeapp.generated.resources.data_error
import pickstream.composeapp.generated.resources.error_no_internet
import pickstream.composeapp.generated.resources.error_request_timeout
import pickstream.composeapp.generated.resources.error_serialization
import pickstream.composeapp.generated.resources.error_too_many_requests
import pickstream.composeapp.generated.resources.error_unknown

fun DataError.toUiText(): UiText {
    val stringRes = when(this) {
        DataError.Local.DATA_ERROR -> Res.string.data_error
        DataError.Local.UNKNOWN -> Res.string.error_unknown
        DataError.Remote.REQUEST_TIMEOUT -> Res.string.error_request_timeout
        DataError.Remote.TOO_MANY_REQUESTS -> Res.string.error_too_many_requests
        DataError.Remote.NO_INTERNET -> Res.string.error_no_internet
        DataError.Remote.SERVER -> Res.string.error_unknown
        DataError.Remote.SERIAlIZATION -> Res.string.error_serialization
        DataError.Remote.UNKNOWN -> Res.string.error_unknown
    }

    return UiText.StringResourceId(stringRes)
}