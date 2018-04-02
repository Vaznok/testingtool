package com.epam.rd.services.impl;

import com.epam.rd.data.access.domain.User;
import com.epam.rd.data.access.repository.UserRepository;
import com.epam.rd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements  UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User find(Long id) {
        return userRepository.findById(id).get();
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
}
