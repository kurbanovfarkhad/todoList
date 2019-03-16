package com.example.task.repo;

import com.example.task.domain.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskList,Long> {
}
