package ru.otus.projectwork.libraryservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.projectwork.libraryservice.domain.User;

import static org.assertj.core.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.otus.projectwork.libraryservice.test.TokenUtility.getToken;

@DisplayName("Общие тесты")
@SpringBootTest
@AutoConfigureMockMvc
class SecurityTest {

    public static final String JANE_DOE = "Jane Doe";
    public static final String JANE_DOE_PASSWORD = "J@n3w0rd";
    private static final String WRONG_CASE = "Wrong case";
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Value("${security.header}")
    private String securityHeader;

    @Value("${security.sign-up-url}")
    private String securitySignUpUrl;

    @Value("${security.token-prefix}")
    private String securityTokenPrefix;

    @DisplayName("Доступность для неавторизованных")
    @ParameterizedTest
    @ValueSource(strings = {
            "GET /authors",
            "GET /books",
            "GET /books/1",
            "GET /genres",
            "POST /books",
            "PUT /books",
            "DELETE /books/1"
    })
    void testByUnauthorized(String methodWithUrlTemplate) throws Exception {
        var parts = methodWithUrlTemplate.split(" ");
        final var method = parts[0];
        final var urlTemplate = parts[1];
        switch (method) {
            case "GET" -> mvc.perform(get(urlTemplate))
                    .andExpect(status().isForbidden());
            case "POST" -> mvc.perform(post(urlTemplate))
                    .andExpect(status().isForbidden());
            case "PUT" -> mvc.perform(put(urlTemplate))
                    .andExpect(status().isForbidden());
            case "DELETE" -> mvc.perform(delete(urlTemplate))
                    .andExpect(status().isForbidden());
            default -> fail(WRONG_CASE);
        }
    }

    @DisplayName("Доступность для роли USER")
    @ParameterizedTest
    @ValueSource(strings = {
            "POST /books",
            "PUT /books",
            "DELETE /books/1"
    })
    void testByUser(String methodWithUrlTemplate) throws Exception {
        var token = getToken(User.builder().userName(JANE_DOE).password(JANE_DOE_PASSWORD).build(), mvc, mapper, securitySignUpUrl, securityTokenPrefix);
        var parts = methodWithUrlTemplate.split(" ");
        final var method = parts[0];
        final var urlTemplate = parts[1];
        switch (method) {
            case "POST" -> mvc.perform(post(urlTemplate).header(securityHeader, token))
                    .andExpect(status().isForbidden());
            case "PUT" -> mvc.perform(put(urlTemplate).header(securityHeader, token))
                    .andExpect(status().isForbidden());
            case "DELETE" -> mvc.perform(delete(urlTemplate).header(securityHeader, token))
                    .andExpect(status().isForbidden());
            default -> fail(WRONG_CASE);
        }
    }
}
