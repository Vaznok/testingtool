package com.epam.rd.services.dao.impl;

import com.epam.rd.data.access.domain.Authority;
import com.epam.rd.data.access.domain.User;
import com.epam.rd.data.access.domain.UserRoles;
import com.epam.rd.data.access.repository.AuthorityRepository;
import com.epam.rd.data.access.repository.UserRepository;
import com.epam.rd.services.dao.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements  UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User find(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User add(User model) {
        return userRepository.save(model);
    }

    @Override
    public User update(User model) {
        return userRepository.save(model);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Long saveUserWithRole(User user, UserRoles userRole) {
        Authority authority = new Authority();
        authority.setUsername(user.getEmail());
        authority.setAuthority(userRole);
        authorityRepository.save(authority);
        return userRepository.save(user).getId();
    }
}
