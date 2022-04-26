package com.iwelogic.data.sign_in.login

import com.iwelogic.core.Mapper
import com.iwelogic.data.models.UserData
import com.iwelogic.domain.models.UserDomain

class UserDataDomainMapper : Mapper<UserData, UserDomain> {

    override fun map(input: UserData): UserDomain {
        return UserDomain(
            objectId = input.objectId,
            firstName = input.firstName,
            lastName = input.lastName,
            image = input.image,
            userToken = input.userToken,
            email = input.email
        )
    }

    override fun reverseMap(input: UserDomain): UserData {
        return UserData(
            objectId = input.objectId,
            firstName = input.firstName,
            lastName = input.lastName,
            image = input.image,
            userToken = input.userToken,
            email = input.email
        )
    }
}