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

            UNKNOWN,

            NO_CONNECTION,

            WRONG_EMAIL,

            WRONG_PASSWORD,

            PASSWORD_TWO_DOESNT_MATCH,

            PASSWORD_TOO_SHORT,

            PASSWORDS_DONT_MATCH,

            AUTH;
        }
    }

    object Loading : Result<Nothing>()

    object Finish : Result<Nothing>()
}

