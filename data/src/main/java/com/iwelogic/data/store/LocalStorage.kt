package com.iwelogic.data.store

import com.iwelogic.data.models.User
import kotlinx.coroutines.flow.Flow

interface LocalStorage {

    var userFlow: Flow<User>
}