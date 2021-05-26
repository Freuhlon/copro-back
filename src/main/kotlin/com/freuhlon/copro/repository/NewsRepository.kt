package com.freuhlon.copro.repository

import com.freuhlon.copro.model.NewsRead
import com.freuhlon.copro.model.NewsWrite
import java.time.LocalDateTime

interface NewsRepository {
    fun getAll(): List<NewsRead>
    fun save(news: NewsWrite, login: String, date: LocalDateTime)
    fun delete(id: Long)
}
