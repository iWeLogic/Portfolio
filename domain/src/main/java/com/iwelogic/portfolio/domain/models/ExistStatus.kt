package com.iwelogic.portfolio.domain.models

sealed class ExistStatus {

    object True : ExistStatus()

    object False : ExistStatus()
}