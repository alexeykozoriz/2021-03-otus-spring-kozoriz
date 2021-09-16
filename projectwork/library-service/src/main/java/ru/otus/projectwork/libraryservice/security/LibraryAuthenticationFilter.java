package ru.otus.projectwork.libraryservice.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Фильтр библиотеки
 */
public class LibraryAuthenticationFilter extends BasicAuthenticationFilter {

    private static final String X_AUTHORITIES = "X-Authorities";

    public LibraryAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    /**
     * Добавление в заголовок ответа информации для фронтeнда
     *
     * @param request    запрос
     * @param response   ответ
     * @param authResult результат аутентификации
     * @throws IOException исключение
     */
    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
        final var principal = (UserDetailsImpl) authResult.getPrincipal();
        final String authorities = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());
        response.addHeader("Access-Control-Allow-Headers", X_AUTHORITIES);
        response.addHeader("Access-Control-Expose-Headers", X_AUTHORITIES);
        response.addHeader(X_AUTHORITIES, authorities);
    }
}
