package com.iwelogic.presentation.ui.main.feedbacks

import com.iwelogic.core.Mapper
import com.iwelogic.domain.models.FeedbackDomain
import com.iwelogic.presentation.models.FeedbackPresentation
import javax.inject.Inject

class FeedbackDomainPresentationMapper @Inject constructor() : Mapper<FeedbackDomain, FeedbackPresentation> {

    override fun map(input: FeedbackDomain): FeedbackPresentation {
        return FeedbackPresentation(
            created = input.created,
            name = input.name,
            rating = input.rating?.toFloat(),
            text = input.text,
            objectId = input.objectId
        )
    }

    override fun reverseMap(input: FeedbackPresentation): FeedbackDomain {
        return FeedbackDomain(
            created = input.created,
            name = input.name,
            rating = input.rating?.toInt(),
            text = input.text,
            objectId = input.objectId
        )
    }
}