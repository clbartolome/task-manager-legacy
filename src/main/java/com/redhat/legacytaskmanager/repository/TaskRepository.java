package com.redhat.legacytaskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redhat.legacytaskmanager.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}