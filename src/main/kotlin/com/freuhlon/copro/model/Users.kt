package com.freuhlon.copro.model


data class UserRead(
    val login: String,
    val firstname: String,
    val lastname: String
)

data class UserSignup(
    val login: String,
    val firstname: String,
    val lastname: String,
    val password: String,
)

data class UserWrite(
    val username: String,
    val firstname: String,
    val lastname: String,
    var password: String,
    val password2: String,
)
