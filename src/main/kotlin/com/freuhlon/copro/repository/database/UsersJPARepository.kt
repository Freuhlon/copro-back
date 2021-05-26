package com.freuhlon.copro.repository.database

import com.freuhlon.copro.repository.database.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersJPARepository: JpaRepository<UserEntity, Long>  {

    fun getUsersEntityByLogin(login: String?): UserEntity?
}
