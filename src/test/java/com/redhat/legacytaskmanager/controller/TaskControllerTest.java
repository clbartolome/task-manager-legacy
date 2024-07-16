package com.redhat.legacytaskmanager.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.hibernate.mapping.List;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.redhat.legacytaskmanager.model.Task;
import com.redhat.legacytaskmanager.repository.TaskRepository;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testListTasks() throws Exception {
        mockMvc.perform(get("/tasks"))
        .andExpect(status().isOk())
        .andExpect(view().name("tasks/list"))
        .andExpect(model().attributeExists("tasks"));
        }


    @Test
    public void testCreateTask() throws Exception {
        mockMvc.perform(post("/tasks")
                .param("title", "New Task")
                .param("description", "New Description")
                .param("dueDate", LocalDate.now().plusDays(1).toString()))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/tasks"));

        ArrayList<Task> tasks = (ArrayList<Task>) taskRepository.findAll();
        boolean exists = false;
        assertNotNull(tasks);
        for (Task task : tasks) {
            if (task.getTitle().equals("New Task")) exists = true;
        }
        assertTrue(exists);
    }

    @Test
    public void testCompleteTask() throws Exception {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setDueDate(LocalDate.now().plusDays(1));
        String aaa = task.getDueDate().toString();
        taskRepository.save(task);

        mockMvc.perform(post("/tasks/complete/" + task.getId()))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/tasks"));

        Task completedTask = taskRepository.findById(task.getId()).orElse(null);
        assertNotNull(completedTask);
        assertTrue(completedTask.isCompleted());
    }
}
