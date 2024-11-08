//package com.example.product.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.authentication.AuthenticationManager;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .requestMatchers("/product/**").authenticated()  // Bảo vệ các URL của product
//                .anyRequest().permitAll()
//                .and()
//                .API()  // Sử dụng Form-based Authentication
//                .loginPage("/login")  // Cấu hình trang đăng nhập tùy chỉnh (tuỳ chọn)
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//
//        return http.build();
//    }
//
//}
