package com.example.securityflaws.common.config;

import com.example.securityflaws.common.security.BadPasswordException;
import com.example.securityflaws.common.security.BadUserNameException;
import com.example.securityflaws.common.security.FlawedAuthProvider;
import com.example.securityflaws.common.security.MyBasicAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/customers").permitAll()
                .antMatchers("/customers/**").permitAll()

                .antMatchers("/flaws/xxe").permitAll()
                .antMatchers("/flaws/xxe/**").permitAll()

                .antMatchers("/flaws/injection/**").permitAll()
                .antMatchers("/flaws/credentials").authenticated()
                .and()
                .httpBasic()
                .and()
                .addFilterBefore(new MyBasicAuthFilter(authenticationManager()), BasicAuthenticationFilter.class)
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(new FlawedAuthProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Bean
    public DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher() {
        final var publisher = new DefaultAuthenticationEventPublisher();

        publisher.setAdditionalExceptionMappings(
                Map.of(
                        BadPasswordException.class, AuthenticationFailureBadCredentialsEvent.class,
                        BadUserNameException.class, AuthenticationFailureBadCredentialsEvent.class)
        );

        return publisher;
    }
}
