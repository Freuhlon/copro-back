package com.freuhlon.copro.repository.database

import com.freuhlon.copro.repository.database.entity.NewsEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NewsJPARepository: JpaRepository<NewsEntity, Long>  {
}
