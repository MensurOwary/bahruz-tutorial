package com.example.bahruztutorial;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        Todo a=new Todo();
        a.setTitle("test1");
        a.setCategory("test1");
        a.setDeadline(LocalDate.of(2022,4,18));

        Todo a1=new Todo();
        a1.setTitle("test2");
        a1.setCategory("test2");
        a1.setDeadline(LocalDate.of(2022,5,18));
        System.out.println(todoService.addTodo(a));
        System.out.println(todoService.addTodo(a1));

        Todo a2=new Todo();
        a2.setTitle("test3");
        a2.setCategory("test3");
        a2.setDeadline(LocalDate.of(2022,3,18));

        Todo a3=new Todo();
        a3.setTitle("test4");
        a3.setCategory("test4");
        a3.setDeadline(LocalDate.of(2022,4,20));

        Todo a4=new Todo();
        a4.setTitle("test5");
        a4.setCategory("test5");
        a4.setDeadline(LocalDate.of(2022,4,21));
        System.out.println(todoService.addTodo(a2));
        System.out.println(todoService.addTodo(a3));
        System.out.println(todoService.addTodo(a4));
    }

    @PostMapping
    public ResponseEntity<String> addTodo(@RequestBody Todo todo) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(todoService.addTodo(todo));
        } catch (Exception exception) {
            ResponseEntity.status(HttpStatus.CREATED).body(todoService.addTodo(todo));
            return ResponseEntity
                    .unprocessableEntity()
                    .body(exception.getMessage());
        }
    }

    @GetMapping("/all")
    public Collection<Todo> getAllTodo() {
        return todoService.getAllTodo();
    }

    @GetMapping("/all/2days")
    public Collection<Todo> get2Days() {
        return todoService.get2Days();
    }

    @GetMapping("/{id}") 
    public Todo getTodo(@PathVariable String id) {
        return todoService.getWithId(id);
    }

    @GetMapping("/category/{category}")
    public Todo getTodoWithCategory(@PathVariable String category) {
        return todoService.getWithCategory(category);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> updateTodo(@PathVariable String id,@RequestBody Todo todo) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(todoService.updateTodo(id,todo));
    }

    @PostMapping("/{id}/done")
    public ResponseEntity<String> doneTodo(@RequestBody Todo todo) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable String id) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(todoService.deleteTodoWithId(id));
    }

}
