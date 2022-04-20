package com.iwelogic.domain.models

sealed class ExistStatus {

    object True : ExistStatus()

    object False : ExistStatus()
}