package com.chat.service.impl;

import com.chat.Entity.User;
import com.chat.repository.UserRepository;
import com.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public Page<User> findList(Pageable pageable) {
        return userRepository.findList(pageable);
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Integer deleteById(Integer id) {
        return userRepository.deleteById(id);
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }
}
