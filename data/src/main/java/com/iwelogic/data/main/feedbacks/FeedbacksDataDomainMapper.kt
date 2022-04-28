package com.iwelogic.data.main.feedbacks

import com.iwelogic.core.Mapper
import com.iwelogic.data.models.FeedbackData
import com.iwelogic.domain.models.FeedbackDomain

class FeedbacksDataDomainMapper : Mapper<FeedbackData, FeedbackDomain> {

    override fun map(input: FeedbackData): FeedbackDomain {
        return FeedbackDomain(
            created = input.created,
            name = input.name,
            rating = input.rating,
            text = input.text,
            objectId = input.objectId
        )
    }

    override fun reverseMap(input: FeedbackDomain): FeedbackData {
        return FeedbackData(
            created = input.created,
            name = input.name,
            rating = input.rating,
            text = input.text,
            objectId = input.objectId
        )
    }
}