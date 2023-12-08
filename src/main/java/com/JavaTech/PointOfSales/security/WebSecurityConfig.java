package com.JavaTech.PointOfSales.security;

import com.JavaTech.PointOfSales.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/static/**").permitAll()
                                .requestMatchers("/assets/**").permitAll()
                                .requestMatchers("/user-photos/**").permitAll()
                                .requestMatchers("/customers/**").permitAll()
                                .requestMatchers("/user/confirm-account").permitAll()
                                .requestMatchers("/api/test/admin").hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/api/test/user").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                                .anyRequest().authenticated())


                .formLogin(formlogin -> formlogin
                                .loginPage("/api/auth/login")
                                .loginProcessingUrl("/j_spring_security_login")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .permitAll()
//                        .failureForwardUrl("/api/auth/login_fail")
//                        .defaultSuccessUrl("/", true)
                                .successHandler(loginSuccessHandler)
                )

                .logout((logout) ->
                        logout.deleteCookies("remove")
                                .invalidateHttpSession(false)
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/api/auth/login")
                                .permitAll())
                .rememberMe(rm ->
                        rm.tokenValiditySeconds(7 * 24 * 60 * 60)
                                .key("AbcdefghiJklmNoPqRstUvXyz"))
                .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedPage("/access-denied"));

        http.authenticationProvider(authenticationProvider());
        return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
