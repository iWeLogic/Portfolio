package com.iwelogic.presentation.ui.main.profile

import com.iwelogic.core.Mapper
import com.iwelogic.domain.models.UserDomain
import com.iwelogic.presentation.models.UserPresentation
import javax.inject.Inject

class UserDomainPresentationMapper @Inject constructor() : Mapper<UserDomain, UserPresentation> {

    override fun map(input: UserDomain): UserPresentation {
        return UserPresentation(
            objectId = input.objectId,
            firstName = input.firstName,
            lastName = input.lastName,
            image = input.image,
            userToken = input.userToken,
            email = input.email
        )
    }

    override fun reverseMap(input: UserPresentation): UserDomain {
        return UserDomain(
            objectId = input.objectId,
            firstName = input.firstName,
            lastName = input.lastName,
            image = input.image,
            userToken = input.userToken,
            email = input.email
        )
    }
}