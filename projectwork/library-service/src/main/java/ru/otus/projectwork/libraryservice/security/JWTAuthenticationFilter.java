package ru.otus.projectwork.libraryservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.otus.projectwork.libraryservice.domain.User;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Фильтр аутентификации
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final long securityExpirationTime;
    private final String securitySecret;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
                                   @Value("${security.sign-up-url}") String securitySignUpUrl,
                                   @Value("${security.expiration-time}") long securityExpirationTime,
                                   @Value("${security.secret}") String securitySecret) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(securitySignUpUrl + "/login");
        this.securityExpirationTime = securityExpirationTime;
        this.securitySecret = securitySecret;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            final var user = new ObjectMapper().readValue(req.getInputStream(), User.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth)
            throws IOException {
        final var principal = (UserDetailsImpl) auth.getPrincipal();
        final var authorities = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        final String token = JWT.create()
                .withSubject(principal.getUsername())
                .withClaim("authorities", authorities)
                .withExpiresAt(new Date(System.currentTimeMillis() + securityExpirationTime))
                .sign(Algorithm.HMAC512(securitySecret.getBytes()));
        res.getWriter().write(token);
        res.getWriter().flush();
    }
}
