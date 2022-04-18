package com.example.bahruztutorial;

import org.slf4j.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
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
        entity.setCategory(todo.getCategory());
        entity.setDeadline(todo.getDeadline());
        entity.setId(id);

        repository.save(entity);

        return id;
    }

    public Collection<Todo> getAllTodo() {
        Collection<Todo> todos = new ArrayList<>();

        for (TodoModel todoModel : repository.findAll()) {
            Todo todo = new Todo();
            setForTodo(todo,todoModel);
            todos.add(todo);
        }

        return todos;
    }

    public Todo getWithId(String id) {
        Todo todo = new Todo();
        for (TodoModel todoModel : repository.findAll()) {
            if(todoModel.getId().equals(id)){
                setForTodo(todo,todoModel);
                return todo;
            }
        }
                return todo;
    }


    public Todo getWithCategory(String category) {
        Todo todo = new Todo();
        for (TodoModel todoModel : repository.findAll()) {
            if(todoModel.getCategory().equals(category)){
                setForTodo(todo,todoModel);
                return todo;
            }
        }
        return todo;
    }


    public Collection<Todo> get2Days(){
        Collection<Todo> todos = new ArrayList<>();

        for(TodoModel todoModel: repository.findAll()){
            if(ChronoUnit.DAYS.between(LocalDate.now(),todoModel.getDeadline())<=2){
                Todo todo = new Todo();
                setForTodo(todo,todoModel);
                todos.add(todo);
            }
        }
        return todos;

    }
    private void  setForTodo(Todo todo,TodoModel todoModel){
        todo.setTitle(todoModel.getTitle());
        todo.setCategory(todoModel.getCategory());
        todo.setDeadline(todoModel.getDeadline());


    }

}
