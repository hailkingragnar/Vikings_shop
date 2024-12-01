package org.web.vikings_shop.config;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.web.vikings_shop.service.impl.UserDetailSecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class UserSecurityConfig {

    @Autowired
    private AuthenticationSuccessHandlerOauth2 handler;

    @Autowired
    private UserDetailSecurityService userDetailSecurityService;
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->{
          authorizeRequests .requestMatchers("/home", "/login" , "/signup" , "/", "/do-register").permitAll()
                  .requestMatchers("/css/**", "/script/**", "/images/**", "/webjars/**").permitAll() // Allow static resources
                    .requestMatchers("/admin/**").hasRole("ADMIN") // Admin-only access
                    .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN") // User access
                    .anyRequest().authenticated();
        });
        http.formLogin(formLogin -> {
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.defaultSuccessUrl("/user/userhome" , true);
            formLogin.failureUrl("/login?error=true");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
        });

        http.oauth2Login(oauth2Login -> {
            oauth2Login.loginPage("/login");
            oauth2Login.successHandler(handler);
        });




        http.csrf(AbstractHttpConfigurer::disable);
        http.logout(logout -> {
            logout.logoutUrl("/logout");
            logout.logoutSuccessUrl("/login?logout=true");
        });



        return http.build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailSecurityService);
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());

        Logger logger = LoggerFactory.getLogger(UserSecurityConfig.class);


        return daoAuthenticationProvider;



    }
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}