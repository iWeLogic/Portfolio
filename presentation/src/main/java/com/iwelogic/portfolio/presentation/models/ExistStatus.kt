package com.iwelogic.portfolio.presentation.models

sealed class ExistStatus {

    object True : ExistStatus()

    object False : ExistStatus()
}