package com.iwelogic.data.store

import com.iwelogic.data.models.User
import kotlinx.coroutines.flow.Flow

interface DataStorageRepository {

    var userFlow: Flow<User>
}