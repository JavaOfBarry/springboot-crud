package com.chat.service;

import com.chat.Entity.User1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    Page<User1> findList(Pageable pageable);

    User1 findById(Integer id);

    User1 findByUserName(String userName);

    Integer deleteById(Integer id);

    void addUser(User1 user);
}
