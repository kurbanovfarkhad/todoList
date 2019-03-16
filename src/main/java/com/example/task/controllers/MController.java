package com.example.task.controllers;

import com.example.task.domain.User;
import com.example.task.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MController {
    private final TaskRepository taskRepository;
    @Autowired
    public MController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/")
    public String main(Model model, @AuthenticationPrincipal User user){

        HashMap<Object, Object> data = new HashMap<>();
        data.put("profile",user);
        data.put("tasks",taskRepository.findAll());
        model.addAttribute("frontendData", data);
        return "index";
    }
}
