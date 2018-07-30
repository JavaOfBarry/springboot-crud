package com.chat.repository;

import com.chat.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {
	@Query("SELECT u FROM User u")
	Page<User> findList(Pageable pageable);

	User findById(Integer id);

	User findByUserName(String userName);

	Integer deleteById(Integer id);
}
