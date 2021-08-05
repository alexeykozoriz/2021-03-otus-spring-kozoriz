package ru.otus.homework20210609.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Фильтр авторизации
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final String securityHeader;
    private final String securityTokenPrefix;
    private final String securitySecret;

    public JWTAuthorizationFilter(AuthenticationManager authManager,
                                  @Value("security.header") String securityHeader,
                                  @Value("security.token-prefix") String securityTokenPrefix,
                                  @Value("security.secret") String securitySecret) {
        super(authManager);
        this.securityHeader = securityHeader;
        this.securityTokenPrefix = securityTokenPrefix;
        this.securitySecret = securitySecret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        final String header = req.getHeader(securityHeader);
        if (header == null || !header.startsWith(securityTokenPrefix)) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(securityHeader);
        if (token != null) {
            final var decodedJWT = JWT.require(Algorithm.HMAC512(securitySecret.getBytes()))
                    .build()
                    .verify(token.replace(securityTokenPrefix, "").trim());
            final String user = decodedJWT.getSubject();
            final var authorities = decodedJWT.getClaim("authorities").asList(String.class).stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }
            return null;
        }
        return null;
    }
}
