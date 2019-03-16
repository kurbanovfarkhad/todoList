package com.example.task.controllers;

import com.example.task.domain.TaskList;
import com.example.task.domain.Views;
import com.example.task.repo.TaskRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/taskList")
public class MainController {
    private final TaskRepository taskRepository;

    public MainController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public List<TaskList> list(){
        return taskRepository.findAll();
    }
    @GetMapping("{id}")
    public TaskList getOne(@PathVariable("id") TaskList taskList){
        return taskList;
    }
    @PostMapping
    public TaskList create(@RequestBody TaskList taskList){

        return taskRepository.save(taskList);
    }

    @PutMapping("{id}")
    public TaskList update(
            @PathVariable("id") TaskList taskFromDb,
            @RequestBody TaskList taskList
    ){
        BeanUtils.copyProperties(taskList,taskFromDb,"id");
        return taskRepository.save(taskFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") TaskList taskList){
        taskRepository.delete(taskList);
    }
}
