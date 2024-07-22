package dev.fcolucas.spring_security_jwt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.boot.web.servlet.ServletRegistrationBean;

@Configuration
public class WebSecurityConfig {
  @Bean
  public BCryptPasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  private static final String[] SWAGGER_WHITELIST = {
      "/v2/api-docs",
      "/swagger-resources",
      "/swagger-resources/**",
      "/configuration/ui",
      "/configuration/security",
      "/swagger-ui.html",
      "/webjars/**"
  };

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors(cors -> cors.disable())
        .csrf(csrf -> csrf.disable())
        .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
        .addFilterAfter(new JWTFilter(), UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(SWAGGER_WHITELIST).permitAll()
            .requestMatchers("/h2-console/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/login").permitAll()
            .requestMatchers(HttpMethod.POST, "/users").permitAll()
            .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("USERS", "MANAGERS")
            .requestMatchers("/managers").hasAnyRole("MANAGERS"))
        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    return http.build();
  }

  // @Bean // HABILITANDO ACESSAR O H2-DATABSE NA WEB
  // public ServletRegistrationBean h2servletRegistration() {
  // ServletRegistrationBean registrationBean = new ServletRegistrationBean();
  // registrationBean.addUrlMappings("/h2-console/*");
  // return registrationBean;
  // }
}
