package com.iwelogic.domain.sign_in.register


import com.iwelogic.core.utils.isEmail
import com.iwelogic.domain.models.DomainRegister
import com.iwelogic.domain.models.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class RegisterUseCaseImp(private val registerRepository: RegisterRepository) : RegisterUseCase {

    override fun register(email: String?, firstName: String?, lastName: String?, passwordOne: String?, passwordTwo: String?): Flow<Result<Any>> {

        val errors: MutableList<Result.Error> = ArrayList()
        if (!email.isEmail()) errors.add(Result.Error(Result.Error.Code.WRONG_EMAIL))
        if (passwordOne.isNullOrEmpty() || passwordOne.length < 8) errors.add(Result.Error(Result.Error.Code.WRONG_PASSWORD))
        if (passwordTwo != passwordOne) errors.add(Result.Error(Result.Error.Code.PASSWORD_TWO_DOESNT_MATCH))
        if (errors.isNotEmpty()) return errors.asFlow()

        return registerRepository.register(DomainRegister(email = email, firstName = firstName, lastName = lastName, password = passwordOne))
    }
}