package ru.otus.homework20210609.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.homework20210609.domain.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Утилитный класс для получения токенов JWT
 */
public final class TokenUtility {

    /**
     * Токен JWT для пользователя в тестовом контексте
     */
    public static String getToken(User user, MockMvc mvc, ObjectMapper mapper, String securitySignUpUrl, String securityTokenPrefix) {
        try {
            return securityTokenPrefix + " " + mvc.perform(post(securitySignUpUrl + "/login")
                    .content(mapper.writeValueAsString(user)))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse().getContentAsString()
                    .replace(user.getUserName(), "").trim();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
