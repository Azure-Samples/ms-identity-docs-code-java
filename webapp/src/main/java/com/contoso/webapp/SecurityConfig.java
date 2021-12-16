package com.contoso.webapp;

import com.azure.spring.aad.webapp.AADWebSecurityConfigurerAdapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends AADWebSecurityConfigurerAdapter {

    @Value("$app.protect.unauthenticated")
    private String[] unprotectedRoutes;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        http.authorizeRequests()
                .antMatchers("/").permitAll() // Only home page can be accessed anon
                .anyRequest().authenticated(); // Everything else is protected
    }
}
