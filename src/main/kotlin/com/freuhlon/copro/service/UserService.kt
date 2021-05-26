package com.freuhlon.copro.service

import com.freuhlon.copro.config.JwtTokenUtil
import com.freuhlon.copro.model.JwtRequest
import com.freuhlon.copro.model.UserRead
import com.freuhlon.copro.model.UserWrite
import com.freuhlon.copro.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired
    private val repository: UserRepository,

    @Autowired
    private val bcryptEncoder: PasswordEncoder,

    @Autowired
    private val authenticationManager: AuthenticationManager,

    @Autowired
    private val jwtTokenUtil: JwtTokenUtil,

    @Autowired
    private val userDetailsService: JwtUserDetailsService
) {

    fun getUser(login: String): UserRead {
        return this.repository.readUser(login)
    }

    fun signup(user: UserWrite): String {

        val userExist: Boolean = this.repository.userExist(user.username)
        if (userExist) {
            throw Exception("Utilisateur déjà présent")
        }

        user.password = bcryptEncoder.encode(user.password)
        this.repository.save(user)
        return this.login(JwtRequest(
            username = user.username,
            password = user.password2
        ))
    }

    fun login(authenticationRequest: JwtRequest): String {
        authenticate(authenticationRequest.username, authenticationRequest.password);
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(authenticationRequest.username);
        return jwtTokenUtil.generateToken(userDetails);
    }

    @Throws(Exception::class)
    private fun authenticate(username: String, password: String) {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: DisabledException) {
            throw Exception("USER_DISABLED", e)
        } catch (e: BadCredentialsException) {
            throw Exception("INVALID_CREDENTIALS")
        }
    }
}
