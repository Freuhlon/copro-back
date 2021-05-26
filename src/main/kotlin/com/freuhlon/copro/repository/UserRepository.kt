package com.freuhlon.copro.repository

import com.freuhlon.copro.model.UserRead
import com.freuhlon.copro.model.UserSignup
import com.freuhlon.copro.model.UserWrite
import org.springframework.security.core.userdetails.UserDetails

interface UserRepository {
    fun checkUser(login: String?) : UserDetails
    fun signUp(signUp: UserSignup)
    fun userExist(login: String): Boolean
    fun readUser(login: String): UserRead
    fun save(user: UserWrite)
}
