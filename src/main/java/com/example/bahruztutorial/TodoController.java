package com.example.bahruztutorial;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
controller-service-database
/todo

 POST /todo {...} -> creates
 GET /todo -> all todos
 GET /todo/{id} -> gets todo with id
 DELETE /todo/{id} -> deletes
 POST /todo/{id} -> updating/done

 */

@RestController
@RequestMapping(value = "todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<String> addTodo(@RequestBody Todo todo) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(todoService.addTodo(todo));
        } catch (Exception exception) {
            return ResponseEntity
                    .unprocessableEntity()
                    .body(exception.getMessage());
        }
    }

    @GetMapping
    public Collection<Todo> getAllTodo() {
        return todoService.getAllTodo();
    }

    @GetMapping("/{id}")
    public String getTodo(@PathVariable String id) {
        return "get - " + id;
    }

    @PostMapping("/{id}")
    public String updateTodo(@PathVariable String id) {
        return "update - " + id;
    }

    @PostMapping("/{id}/done")
    public String doneTodo(@PathVariable String id) {
        return "done - " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteTodo(@PathVariable String id) {
        return "delete - " + id;
    }

}
