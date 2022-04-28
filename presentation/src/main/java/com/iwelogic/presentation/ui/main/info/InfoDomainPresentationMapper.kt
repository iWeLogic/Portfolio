package com.iwelogic.presentation.ui.main.info

import com.iwelogic.core.Mapper
import com.iwelogic.domain.models.InfoDomain
import com.iwelogic.presentation.models.InfoPresentation
import javax.inject.Inject

class InfoDomainPresentationMapper @Inject constructor() : Mapper<InfoDomain, InfoPresentation> {

    override fun map(input: InfoDomain): InfoPresentation {
        return InfoPresentation(
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

    override fun reverseMap(input: InfoPresentation): InfoDomain {
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