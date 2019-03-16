package com.example.task.controllers;

import com.example.task.domain.TaskList;
import com.example.task.domain.User;
import com.example.task.repo.TaskRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/taskList")
public class MainController {
    private final TaskRepository taskRepository;
    private Logger logger = Logger.getLogger(MainController.class);
    public MainController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public List<TaskList> list(@AuthenticationPrincipal User user, Model model){


        try{
            logger.info("get all task list");
            return taskRepository.findAll();
        }catch(Exception e){
            logger.info("Error "+e.toString());
            return null;
        }
    }
    @GetMapping("{id}")
    public TaskList getOne(@PathVariable("id") TaskList taskList){
        try{
            logger.info("get id: "+taskList.getId()+" note in task list");
            return taskList;
        }catch (Exception e){
            logger.error("Error: "+ e.toString());
            return null;
        }
    }
    @PostMapping
    public TaskList create(@RequestBody TaskList taskList){
        try{
            logger.info("add note to task list");
            return taskRepository.save(taskList);
        }catch (Exception e){
            logger.error("Error: "+ e.toString());
            return null;
        }
    }

    @PutMapping("{id}")
    public TaskList update(
            @PathVariable("id") TaskList taskFromDb,
            @RequestBody TaskList taskList
    ){
        try{
            logger.info("change id: "+taskFromDb.getId()+" note in task list");
            BeanUtils.copyProperties(taskList,taskFromDb,"id");
            return taskRepository.save(taskFromDb);
        }catch (Exception e){
            logger.error("Error: "+ e.toString());
            return null;
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") TaskList taskList){
        try{
            logger.info("remove id: "+taskList.getId()+" note from task list");
            taskRepository.delete(taskList);
        }catch (Exception e){
            logger.error("Error: "+ e.toString());
        }
    }
}
