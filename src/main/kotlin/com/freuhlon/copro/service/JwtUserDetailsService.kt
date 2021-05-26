package com.freuhlon.copro.service

import com.freuhlon.copro.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(
    @Autowired
    val repository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        return this.repository.checkUser(username)
    }
}
