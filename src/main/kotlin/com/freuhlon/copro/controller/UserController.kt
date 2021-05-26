package com.freuhlon.copro.controller

import com.freuhlon.copro.model.JwtRequest
import com.freuhlon.copro.model.JwtResponse
import com.freuhlon.copro.model.UserRead
import com.freuhlon.copro.model.UserWrite
import com.freuhlon.copro.service.UserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@Api( description="API user.", tags = ["user"])
@RestController
class UserController(
    @Autowired
    private val service: UserService
) {

    @ApiOperation("Retrieves user info")
    @GetMapping("/user-info")
    fun getAll(principal: Principal): UserRead {
        return service.getUser(principal.name)
    }

    @PostMapping("/signup")
    fun addUser(@RequestBody user: UserWrite): ResponseEntity<Any>  {
        return ResponseEntity.ok(JwtResponse(this.service.signup(user)))
    }

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody authenticationRequest: JwtRequest): ResponseEntity<Any>  {
        return ResponseEntity.ok(JwtResponse(this.service.login(authenticationRequest)))
    }

}
