package com.example.bahruztutorial;

import org.slf4j.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TodoService {

    private static final Logger log = LoggerFactory.getLogger(TodoService.class);

    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public String addTodo(Todo todo) {
        log.info("Received todo with title = {}", todo.getTitle());

        if (todo.getTitle() == null || todo.getTitle().trim().isEmpty()) {
            throw new RuntimeException("Title should not be empty or null");
        }

        TodoModel existingTodo = null;
        for (TodoModel todoModel : repository.findAll()) {
            if (!todoModel.isDone() && todoModel.getTitle().equals(todo.getTitle())) {
                existingTodo = todoModel;
            }
        }

        if (existingTodo != null) {
            return existingTodo.getId();
        }

        String id = UUID.randomUUID().toString();
        TodoModel entity = new TodoModel();
        entity.setTitle(todo.getTitle());
        entity.setId(id);

        repository.save(entity);

        return id;
    }

    public Collection<Todo> getAllTodo() {
        Collection<Todo> todos = new ArrayList<>();

        for (TodoModel todoModel : repository.findAll()) {
            Todo todo = new Todo();
            todo.setTitle(todoModel.getTitle());
            todos.add(todo);
        }

        return todos;
    }
}
