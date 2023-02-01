package com.example.secondcourse.webappdemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.ModelMap;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.validation.BindingResult;
import com.example.secondcourse.webappdemo.services.TodoServiceImpl;
import com.example.secondcourse.webappdemo.model.Todo;
import java.util.*;
import java.time.LocalDate;
//import jakarta.validation.Valid;

@Controller
@Slf4j
public class TodoController {
    
    private TodoServiceImpl todoServiceImpl;
    
    public TodoController(TodoServiceImpl todoServiceImpl) {
        this.todoServiceImpl = todoServiceImpl;
    }
    
    @GetMapping("list-todos")
    public String listAllTodos(ModelMap model) {
        var todoList = todoServiceImpl.getTodos();
        model.addAttribute("todoList", todoList);
        return "listTodos.html";
    }
    
    
    @GetMapping("add-todo")
    public String gotoCreateNewTodo(ModelMap model) {
        Todo newTodo = new Todo();
        model.put("newTodo", newTodo);
        return "createOrUpdateTodo.html";
    }
    
    @PostMapping("add-todo")
    public String addNewTodo(@Valid Todo tD, BindingResult result) {
        
        if (result.hasErrors()) {
            return "createOrUpdateTodo.html";
        } else {
            todoServiceImpl.saveTodo(tD);
            return "redirect:list-todos";
        }
    }
    
    @GetMapping("delete-todo")
    public String deletetodo(@RequestParam int id) {
        log.debug("deleting id: " + Integer.toString(id));
        todoServiceImpl.deleteById(id);
        return "redirect:list-todos";
    }     
}
