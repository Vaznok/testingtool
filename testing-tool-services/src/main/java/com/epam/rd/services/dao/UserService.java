package com.epam.rd.services.dao;

import com.epam.rd.data.access.domain.User;
import com.epam.rd.data.access.domain.UserRoles;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User find(Long id);

    User findUserByEmail(String email);

    User add(User model);

    User update(User model);

    void delete(Long id);

    Long saveUserWithRole(User user, UserRoles userRole);
}
