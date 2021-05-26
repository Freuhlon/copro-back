package com.freuhlon.copro.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
    @Autowired
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,

    @Autowired
    private val jwtUserDetailsService: UserDetailsService,

    @Autowired
    private val jwtRequestFilter: JwtRequestFilter,

    ) : WebSecurityConfigurerAdapter() {

    @Autowired
    private fun configureGlobal(auth: AuthenticationManagerBuilder) {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder

        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder())
    }

    @Bean
    @Override
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Override
    @Throws(Exception::class)
    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity.cors()
            .and()
            .csrf().disable()
            // dont authenticate this particular request
            .authorizeRequests().antMatchers("/authenticate")
            .permitAll()
            .antMatchers(HttpMethod.GET, "/news").permitAll()
            .antMatchers(HttpMethod.POST, "/signup").permitAll()

        // all other requests need to be authenticated
            .anyRequest().authenticated().and().
                // make sure we use stateless session; session won't be used to
                // store user's state.
            exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*");
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
        configuration.allowedHeaders = listOf("authorization", "content-type", "x-auth-token");
        configuration.exposedHeaders = listOf("x-auth-token");
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
