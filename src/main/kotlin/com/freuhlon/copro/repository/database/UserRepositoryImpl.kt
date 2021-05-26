package com.freuhlon.copro.repository.database

import com.freuhlon.copro.model.UserRead
import com.freuhlon.copro.model.UserSignup
import com.freuhlon.copro.model.UserWrite
import com.freuhlon.copro.repository.UserRepository
import com.freuhlon.copro.repository.database.entity.UserEntity
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class UserRepositoryImpl(
    @Autowired
    val repository: UsersJPARepository
) : UserRepository {

    override fun checkUser(login: String?): UserDetails {

        val userFromDB: UserEntity? = this.repository.getUsersEntityByLogin(login)

        if (userFromDB != null) {
            return User(
                userFromDB.login, userFromDB.password,
                listOf(SimpleGrantedAuthority("AUTHOR"))
            )
        }

        throw UsernameNotFoundException("User not found with username: " + login)
    }

    override fun signUp(signUp: UserSignup) {

        val userFromDB: UserEntity? = this.repository.getUsersEntityByLogin(signUp.login)

        if (userFromDB != null) {
           throw Exception("User already exist.")
        }

        this.repository.save(
            UserEntity(
                id = null,
                login = signUp.login,
                firstname = signUp.firstname,
                lastname = signUp.lastname,
                password = signUp.password,
                date = LocalDateTime.now()
            )
        )
    }

    override fun readUser(login: String): UserRead {
        val userFromDB: UserEntity? = this.repository.getUsersEntityByLogin(login)

        if (userFromDB != null) {
            return UserRead(
                login = userFromDB.login,
                firstname = userFromDB.firstname,
                lastname = userFromDB.lastname
            )
        }

        throw UsernameNotFoundException("User not found");

    }

    override fun userExist(login: String): Boolean {
        val userFromDB: UserEntity? = this.repository.getUsersEntityByLogin(login)
        return userFromDB != null
    }

    override fun save(user: UserWrite) {
        this.repository.save(UserEntity(
            id = null,
            login = user.username,
            firstname = user.firstname,
            lastname = user.lastname,
            password = user.password,
            date = LocalDateTime.now()
        ))
    }

    companion object {
        val log = LoggerFactory.getLogger(UserRepositoryImpl::class.java)
    }
}
