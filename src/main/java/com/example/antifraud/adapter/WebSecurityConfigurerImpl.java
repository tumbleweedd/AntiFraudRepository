package com.example.antifraud.adapter;

import com.example.antifraud.exception.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Расширение адаптера и добавление аннотации
@EnableWebSecurity
public class WebSecurityConfigurerImpl extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint = new RestAuthenticationEntryPoint();

    public WebSecurityConfigurerImpl(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/api/auth/user").permitAll()
                .mvcMatchers("/api/antifraud/transaction").hasRole("MERCHANT")
                .mvcMatchers("/api/antifraud/**").hasRole("SUPPORT")
                .mvcMatchers("/api/auth/list").hasAnyRole("SUPPORT", "ADMINISTRATOR")
                .mvcMatchers("/api/auth/**").hasRole("ADMINISTRATOR")
                .anyRequest().authenticated()
                .and()
                .csrf().disable().headers().frameOptions().disable()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.httpBasic()
//                .authenticationEntryPoint(restAuthenticationEntryPoint)
//                .and()
//                .csrf().disable().headers().frameOptions().disable()
//                .and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/api/auth/user").permitAll()
//                .antMatchers("/actuator/shutdown").permitAll()
//                .antMatchers(HttpMethod.GET, "/api/auth/list").authenticated()
//                .antMatchers(HttpMethod.DELETE, "/api/auth/user/{username}").authenticated()
//                .antMatchers(HttpMethod.POST, "/api/antifraud/transaction").authenticated()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
