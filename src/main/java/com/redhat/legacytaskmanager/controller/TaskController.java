package com.redhat.legacytaskmanager.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.redhat.legacytaskmanager.model.Task;
import com.redhat.legacytaskmanager.service.TaskService;

@Controller
@RequestMapping("/tasks")
public class TaskController {

      @Autowired
    private TaskService taskService;

    @GetMapping
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/list";
    }

    @GetMapping("/new")
    public String newTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "tasks/new";
    }

    @PostMapping
    public String createTask(@ModelAttribute Task task, BindingResult result) {
        if (result.hasErrors()) {
            return "tasks/new";
        }
        taskService.save(task);
        return "redirect:/tasks";
    }

    @PostMapping("/complete/{id}")
    public String completeTask(@PathVariable Long id) {
        Task task = taskService.findById(id);
        if (task != null) {
            task.setCompleted(true);
            task.setCompletionDate(LocalDate.now());
            taskService.save(task);
        }
        return "redirect:/tasks";
    }
}