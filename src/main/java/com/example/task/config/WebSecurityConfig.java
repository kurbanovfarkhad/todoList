package com.example.task.config;

import com.example.task.domain.Role;
import com.example.task.domain.User;
import com.example.task.repo.UserDetailsRepo;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**", "/js/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and()
                .csrf().disable();
    }


    @Bean
    public PrincipalExtractor principalExtractor(UserDetailsRepo userDetailRepo) {
        return map -> {
            String id = (String) map.get("sub");

            User user = userDetailRepo.findById(id).orElseGet(() -> {
                User newUser = new User();

                newUser.setId(id);
                newUser.setUserName((String) map.get("name"));
                newUser.setUserEmail((String) map.get("email"));
                newUser.setRoles(Collections.singleton(Role.USER));
                return newUser;
            });

            return userDetailRepo.save(user);
        };
    }

}
