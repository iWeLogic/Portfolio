package com.iwelogic.data.sign_in.login

import com.iwelogic.core.Mapper
import com.iwelogic.data.models.DataUser
import com.iwelogic.domain.models.DomainUser

class UserDataDomainMapper : Mapper<DataUser, DomainUser> {

    override fun map(input: DataUser): DomainUser {
        return DomainUser(
            name = input.name,
            userToken = input.userToken,
            email = input.email
        )
    }
}