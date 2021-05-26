package com.freuhlon.copro.repository.database

import com.freuhlon.copro.model.NewsRead
import com.freuhlon.copro.model.NewsWrite
import com.freuhlon.copro.model.UserRead
import com.freuhlon.copro.repository.NewsRepository
import com.freuhlon.copro.repository.database.entity.NewsEntity
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class NewsRepositoryImpl(
    @Autowired
    val newsRepository: NewsJPARepository,
    @Autowired
    val usersRepository: UsersJPARepository
) : NewsRepository {

    override fun getAll(): List<NewsRead> {
        return this.newsRepository
            .findAll()
            .mapNotNull { mapToDomain(it) }
    }

    override fun save(news: NewsWrite, login: String, date: LocalDateTime) {
        this.newsRepository.save(mapToEntity(news, login, date))
    }

    override fun delete(id: Long) {
        this.newsRepository.deleteById(id)
    }

    private fun mapToEntity(news: NewsWrite, login: String, date: LocalDateTime): NewsEntity {
        return news.let {
            NewsEntity(
                id = null,
                title = it.title,
                type = it.type,
                author = this.usersRepository.getUsersEntityByLogin(login)?.id!!,
                content = it.content,
                date = date
            )
        }
    }

    private fun mapToDomain(entity: NewsEntity?): NewsRead {
        val author = this.usersRepository.getOne(entity!!.author)
        return entity.let {

            NewsRead(
                id = it.id,
                title = it.title,
                type = it.type,
                author = UserRead(
                    login = author.login,
                    firstname = author.firstname,
                    lastname = author.lastname
                ),
                content = it.content,
                date = it.date
            )
        }
    }

    companion object {
        val log = LoggerFactory.getLogger(NewsRepositoryImpl::class.java)
    }
}
