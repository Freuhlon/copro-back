package com.freuhlon.copro.model

import java.time.LocalDateTime


data class NewsRead(
        val id: Long?,
        val title: String,
        val type: String,
        val date: LocalDateTime,
        val content: String,
        val author: UserRead,
)

data class NewsWrite(
        val title: String,
        val type: String,
        val content: String,
)
