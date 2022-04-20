package com.iwelogic.presentation.models

sealed class ExistStatus {

    object True : ExistStatus()

    object False : ExistStatus()
}