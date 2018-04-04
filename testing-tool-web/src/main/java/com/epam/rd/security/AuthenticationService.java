package com.epam.rd.security;

import com.epam.rd.dto.TokenRoleDto;
import com.epam.rd.dto.UserDto;
import org.springframework.security.core.AuthenticationException;

public interface AuthenticationService {

    TokenRoleDto login(final String email, final String password) throws AuthenticationException;

    void registration(UserDto userDto);

    void logout(String token);

}
