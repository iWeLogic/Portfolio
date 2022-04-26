package com.iwelogic.data.sign_in.login

import com.iwelogic.core.Mapper
import com.iwelogic.data.models.UserData
import com.iwelogic.domain.models.UserDomain

class UserDataDomainMapper : Mapper<UserData, UserDomain> {

    override fun map(input: UserData): UserDomain {
        return UserDomain(
            name = input.name,
            userToken = input.userToken,
            email = input.email
        )
    }
}