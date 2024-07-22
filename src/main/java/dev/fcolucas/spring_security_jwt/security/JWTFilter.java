package dev.fcolucas.spring_security_jwt.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // obtem o token da request com AUTHORIZATION
    String token = request.getHeader(JWTCreator.HEADER_AUTHORIZATION);

    try {
      if (token != null && !token.isEmpty()) {
        // create a jwt object with token data
        JWTObject tokenObject = JWTCreator.create(token, SecurityConfig.PREFIX, SecurityConfig.KEY);

        List<SimpleGrantedAuthority> authorities = getAuthorities(tokenObject.getRoles());

        UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
            tokenObject.getSubject(),
            null,
            authorities);

        SecurityContextHolder.getContext().setAuthentication(userToken);
      } else {
        SecurityContextHolder.clearContext();
      }

      filterChain.doFilter(request, response);
    } catch (Exception e) {
      e.printStackTrace();
      response.setStatus(HttpStatus.FORBIDDEN.value());
      return;
    }
  }

  private List<SimpleGrantedAuthority> getAuthorities(List<String> roles) {
    return roles.stream().map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

}
