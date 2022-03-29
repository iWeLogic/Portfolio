package com.iwelogic.portfolio.data.sign_in.login

import com.iwelogic.portfolio.core.Mapper
import com.iwelogic.portfolio.data.models.DataNews
import com.iwelogic.portfolio.data.models.DataUser
import com.iwelogic.portfolio.domain.models.DomainNews
import com.iwelogic.portfolio.domain.models.DomainUser

class UserDataDomainMapper : Mapper<DataUser, DomainUser> {

    override fun map(input: DataUser): DomainUser {
        return DomainUser(
            name = input.name,
            userToken = input.userToken,
            email = input.email
        )
    }
}