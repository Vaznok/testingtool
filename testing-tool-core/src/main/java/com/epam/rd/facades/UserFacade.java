package com.epam.rd.facades;

import com.epam.rd.data.access.domain.User;
import com.epam.rd.dto.TokenRoleDto;
import com.epam.rd.dto.UserDto;

import java.util.List;

public interface UserFacade {

    List<UserDto> findAll();

    UserDto findUserById(Long id);

    UserDto findUserByEmail(String email);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(Long id, UserDto userDto);

    void deleteUserById(Long id);

    TokenRoleDto signIn(String email, String password);

    void logout(String token);

    void registration(UserDto userDto);
}
