package ru.otus.homework20210609.security;

public class SecurityConstants {
    public static final String SECRET = "A78147AB39659C335E683DE127AFC";
    public static final long EXPIRATION_TIME = 900_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/users";
}
