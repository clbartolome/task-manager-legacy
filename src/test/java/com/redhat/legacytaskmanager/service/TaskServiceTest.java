package com.redhat.legacytaskmanager.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.redhat.legacytaskmanager.model.Task;
import com.redhat.legacytaskmanager.repository.TaskRepository;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TaskServiceTest {

  @Autowired
  private TaskService taskService;

  @Autowired
  private TaskRepository taskRepository;

  @Test
  public void testSaveTask() {
    Task task = new Task();
    task.setTitle("Test Task");
    task.setDescription("Test Description");
    task.setDueDate(LocalDate.now().plusDays(1));

    taskService.save(task);
    Task savedTask = taskRepository.findById(task.getId()).orElse(null);

    assertNotNull(savedTask);
    assertEquals("Test Task", savedTask.getTitle());
  }

  @Test
  public void testCompleteTask() {
    Task task = new Task();
    task.setTitle("Test Task");
    task.setDescription("Test Description");
    task.setDueDate(LocalDate.now().plusDays(1));

    taskService.save(task);
    Task savedTask = taskRepository.findById(task.getId()).orElse(null);

    assertNotNull(savedTask);
    savedTask.setCompleted(true);
    savedTask.setCompletionDate(LocalDate.now());
    taskService.save(savedTask);

    Task completedTask = taskRepository.findById(task.getId()).orElse(null);
    assertNotNull(completedTask);
    assertTrue(completedTask.isCompleted());
    assertNotNull(completedTask.getCompletionDate());
  }
}
