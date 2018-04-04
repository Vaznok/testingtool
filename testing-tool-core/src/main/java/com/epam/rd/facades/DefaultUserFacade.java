package com.epam.rd.facades;

import com.epam.rd.data.access.domain.User;
import com.epam.rd.dto.MapStructUserConverter;
import com.epam.rd.dto.TokenRoleDto;
import com.epam.rd.dto.UserDto;
import com.epam.rd.services.dao.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultUserFacade implements UserFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private MapStructUserConverter userConverter;

    @Override
    public List<UserDto> findAll() {
        return userConverter.userListToUserDtoList(userService.findAll());
    }

    @Override
    public UserDto findUserByEmail(String email) {
        return userConverter.userToUserDto(userService.findUserByEmail(email));
    }

    @Override
    public UserDto findUserById(Long id) {
        return userConverter.userToUserDto(userService.find(id));
    }

    @Override
    public void deleteUserById(Long id) {
        userService.delete(id);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userConverter.userDtoToUser(userDto);
        user.setId(id);
        return userConverter.userToUserDto(userService.update(user));
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userConverter.userDtoToUser(userDto);
        return userConverter.userToUserDto(userService.add(user));
    }

    @Override
    public TokenRoleDto signIn(String email, String password) {
        return null;
    }

    @Override
    public void logout(String token) {

    }

    @Override
    public void registration(UserDto userDto) {

    }
}
