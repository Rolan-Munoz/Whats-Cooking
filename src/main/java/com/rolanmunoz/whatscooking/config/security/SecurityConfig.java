package com.rolanmunoz.whatscooking.config.security;

import com.rolanmunoz.whatscooking.config.JWT.JwtAuthenticationEntryPoint;
import com.rolanmunoz.whatscooking.config.JWT.JwtAuthenticationFilter;
import com.rolanmunoz.whatscooking.config.JWT.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final CustomUsersDetailsService customUsersDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, CustomUsersDetailsService customUsersDetailsService, JwtTokenProvider jwtTokenProvider) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.customUsersDetailsService = customUsersDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //verificar la informacion de los usuarios logueados en la api
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //Encriptar passwords
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //filtro de seguridad de jwt
    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(customUsersDetailsService, jwtTokenProvider);
    }

    // establecer cadena de filtros de seguridad
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(String.valueOf(RequestMethod.OPTIONS), "/**").permitAll()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/recipes/**").permitAll()
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/user/**").hasAuthority("USER")
                .requestMatchers("/users/{id}").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers("/users/{id}/recipes").hasAuthority("USER")
                .requestMatchers("/users/{userId}/recipes/{id}").hasAuthority("USER")
                .requestMatchers("/favorites/{userId}/{recipeId}").hasAuthority("USER")
                .requestMatchers("/favorites/{userId}/{recipeId}/isFavorite").hasAuthority("USER")
                .requestMatchers("/favorites/{userId}").hasAuthority("USER")
                .requestMatchers("/comments/{userId}/{recipeId}").hasAuthority("USER")
                .requestMatchers("/comments/{commentId}").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers("/comments/user/{userId}").hasAuthority("USER")
                .requestMatchers("/comments/recipe/{recipeId}").hasAuthority("USER")
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
}
