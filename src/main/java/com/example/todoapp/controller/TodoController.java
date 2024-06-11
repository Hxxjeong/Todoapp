package com.example.todoapp.controller;

import com.example.todoapp.entity.Todo;
import com.example.todoapp.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping
    public List<Todo> getTodos(){
        return todoService.getTodos();
    }
    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable("id") Long id){
        return todoService.getTodo(id);
    }
    @PostMapping
    public Todo addTodo(@RequestBody Todo todo){
        return todoService.addTodo(todo.getTodo());
    }

    @PatchMapping("/{id}")
    public Todo updateTodoById(@PathVariable("id") Long id){
        return todoService.updateTodo(id);
    }

    @PatchMapping
    public Todo updateTodo(@RequestBody Todo todo){
        return todoService.updateTodo(todo.getId());
    }
    @DeleteMapping
    public String deleteTodo(@RequestBody Todo todo){
        todoService.deleteTodo(todo.getId());
        return "ok";
    }
}