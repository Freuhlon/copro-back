package com.freuhlon.copro.repository.database.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "copro_users")
data class UserEntity(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val login: String,
    val firstname: String,
    val lastname: String,
    val password: String,
    val date: LocalDateTime
)
