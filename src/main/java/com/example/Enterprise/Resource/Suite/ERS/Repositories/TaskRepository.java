package com.example.Enterprise.Resource.Suite.ERS.Repositories;

import com.example.Enterprise.Resource.Suite.ERS.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
