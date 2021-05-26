package com.freuhlon.copro.controller

import com.freuhlon.copro.model.NewsRead
import com.freuhlon.copro.model.NewsWrite
import com.freuhlon.copro.service.NewsService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.security.Principal
import java.time.LocalDateTime

@Api( description="API cpro.", tags = ["copro"])
@RestController
class CoproController(
        @Autowired
        val service: NewsService
) {

    @ApiOperation("Retrieves all news")
    @GetMapping("/news")
    fun getAll(): List<NewsRead> {
        return service.getAll()
    }

    @PreAuthorize("hasAuthority('AUTHOR')")
    @PostMapping("/news")
    fun add(@RequestBody news: NewsWrite, principal: Principal) {
        return service.save(news, principal.name, LocalDateTime.now())
    }

    @PreAuthorize("hasAuthority('AUTHOR')")
    @DeleteMapping("/news/{id}")
    fun delete(@PathVariable id: Long, principal: Principal) {
        return service.delete(id)
    }


}
