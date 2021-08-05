package ru.otus.homework20210609.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import ru.otus.homework20210609.domain.User;

import java.util.Collection;
import java.util.Collections;

/**
 * Детализация пользователей
 */
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final var role = user.getRole();
        Assert.isTrue(!role.startsWith("ROLE_"), () -> {
            throw new IllegalArgumentException(role + " cannot start with ROLE_ (it is automatically added)");
        });
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
