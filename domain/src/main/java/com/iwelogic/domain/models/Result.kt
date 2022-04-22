package com.iwelogic.domain.models

import com.google.gson.annotations.SerializedName

sealed class Result<out T> {

    data class Success<out R>(val data: R?) : Result<R>()

    data class Error(val code: Code, val message: String? = null) : Result<Nothing>() {

        enum class Code {

            @SerializedName("3033")
            ALREADY_EXISTS,

            @SerializedName("3087")
            NOT_CONFIRMED,

            @SerializedName("3003")
            INVALID_CREDENTIALS,

            @SerializedName("3020")
            UNABLE_TO_FIND_USER,

            UNKNOWN,

            NO_CONNECTION,

            WRONG_EMAIL,

            PASSWORD_IS_TOO_SHORT,

            WRONG_PASSWORD,

            PASSWORD_TWO_DOESNT_MATCH,
        }
    }

    object Loading : Result<Nothing>()

    object Finish : Result<Nothing>()
}

