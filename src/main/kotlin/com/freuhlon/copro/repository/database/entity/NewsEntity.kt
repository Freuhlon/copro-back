package com.freuhlon.copro.repository.database.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity()
@Table(name = "copro_news")
data class NewsEntity(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val title: String,
    val type: String,
    val author: Long,
    val content: String,
    val date: LocalDateTime
)
