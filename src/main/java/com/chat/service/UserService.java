package com.chat.service;

import com.chat.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<User> findList(Pageable pageable);

    User findById(Integer id);

    User findByUserName(String userName);

    Integer deleteById(Integer id);

    void addUser(User user);
}
