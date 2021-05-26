package com.freuhlon.copro.service

import com.freuhlon.copro.model.NewsRead
import com.freuhlon.copro.model.NewsWrite
import com.freuhlon.copro.repository.NewsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.security.Principal
import java.time.LocalDateTime

@Service
class NewsService(
        @Autowired
        val repository: NewsRepository
) {
    fun getAll(): List<NewsRead> {
        return this.repository.getAll()
    }

    fun save(newsRead: NewsWrite, login: String, date: LocalDateTime) {
        this.repository.save(newsRead, login, date)
    }

    fun delete(id: Long) {
        this.repository.delete(id)
    }
}
