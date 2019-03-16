package com.example.task.repo;

import com.example.task.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepo extends JpaRepository<User,String> {
}
