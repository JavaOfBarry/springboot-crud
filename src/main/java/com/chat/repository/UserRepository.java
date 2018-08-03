package com.chat.repository;

import com.chat.Entity.User1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User1,Long> {
	@Query("SELECT u FROM User1 u")
	Page<User1> findList(Pageable pageable);

	User1 findById(Integer id);

	User1 findByUserName(String userName);

	Integer deleteById(Integer id);
}
