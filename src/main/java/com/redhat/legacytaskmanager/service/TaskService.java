package com.redhat.legacytaskmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redhat.legacytaskmanager.model.Task;
import com.redhat.legacytaskmanager.repository.TaskRepository;

@Service
public class TaskService {

  @Autowired
  private TaskRepository taskRepository;

  public List<Task> findAll() {
    return taskRepository.findAll();
  }

  public Task findById(Long id) {
    return taskRepository.findById(id).orElse(null);
  }

  public void save(Task task) {
    taskRepository.save(task);
  }
}