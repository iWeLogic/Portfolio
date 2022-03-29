package com.iwelogic.portfolio.data.models

sealed class ExistStatus {

    object True : ExistStatus()

    object False : ExistStatus()
}