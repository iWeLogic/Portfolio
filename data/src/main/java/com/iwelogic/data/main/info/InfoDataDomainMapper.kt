package com.iwelogic.data.main.info

import com.iwelogic.core.Mapper
import com.iwelogic.data.models.InfoData
import com.iwelogic.domain.models.InfoDomain

class InfoDataDomainMapper : Mapper<InfoData, InfoDomain> {

    override fun map(input: InfoData): InfoDomain {
        return InfoDomain(
            firstName = input.firstName,
            lastName = input.lastName,
            gitHub = input.gitHub,
            nickName = input.nickName,
            description = input.description,
            avatar = input.avatar,
            email = input.email,
            phone = input.phone,
            telegram = input.telegram,
            skype = input.skype,
            linkedin = input.linkedin,
            upWork = input.upWork,
            resume = input.resume
        )
    }
}