package com.example.task.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table
public class TaskList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.IdName.class)
    private Long id;
    private String task;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "taskDate")
    private Date date;

    @Column(name="checker", columnDefinition = "bool default 0")
    private boolean checker;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isChecker() {
        return checker;
    }

    public void setChecker(boolean checker) {
        this.checker = checker;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
