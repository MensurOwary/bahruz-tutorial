package com.example.bahruztutorial;

import org.slf4j.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class TodoService {

    private static final Logger log = LoggerFactory.getLogger(TodoService.class);

    private final TodoRepository repository;
    private final UserRepository userRepository;

    public TodoService(TodoRepository repository,UserRepository userRepository) {
        this.repository = repository;
        this.userRepository=userRepository;
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
            setForTodoFromTodoModel(todo,todoModel);
            todos.add(todo);
        }

        return todos;
    }

    public Todo getWithId(String id) {
        Todo todo = new Todo();
        for (TodoModel todoModel : repository.findAll()) {
            if(todoModel.getId().equals(id)){
                setForTodoFromTodoModel(todo,todoModel);
                return todo;
            }
        }
                return todo;
    }


    public Todo getWithCategory(String category) {
        Todo todo = new Todo();
        for (TodoModel todoModel : repository.findAll()) {
            if(todoModel.getCategory().equals(category)){
                setForTodoFromTodoModel(todo,todoModel);
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
                setForTodoFromTodoModel(todo,todoModel);
                todos.add(todo);
            }
        }
        return todos;

    }
    private void setForTodoFromTodoModel(Todo todo, TodoModel todoModel){
        todo.setTitle(todoModel.getTitle());
        todo.setCategory(todoModel.getCategory());
        todo.setDeadline(todoModel.getDeadline());


    }

    public String deleteTodoWithId(String id) {
        //repository.deleteById(id); bununla et
        for(TodoModel todoModel: repository.findAll()){
            System.out.println(todoModel.getId());
            System.out.println(id);
            if(todoModel.getId().equals(id)){

                repository.delete(todoModel);
                return "deleted - " + id;

            }
        }
        return "not found - " + id;

    }

    public String updateTodo(String id,Todo todo) {
        for(TodoModel todoModel:repository.findAll()){
            if(todoModel.getId().equals(id)){

                    todoModel.setTitle(todo.getTitle());


                    todoModel.setCategory(todo.getCategory());


                    todoModel.setDeadline(todo.getDeadline());


                repository.save(todoModel);
                return "updated "+id;
            }


        }
        return "id not found " +id;

    }
    public Collection<User> getAllUsers() {
        Collection<User> users = new ArrayList<>();

        for (User user : userRepository.findAll()) {

            users.add(user);
        }

        return users;
    }
    public String addUser(User user){
       if(!CheckUser(user)){
           throw new RuntimeException("Username or Password should not be empty or null");

       }
        String id = UUID.randomUUID().toString();
        user.setId(id);
        userRepository.save(user);
        return user.getUserName();


    }
    public String loginUser(User loginUser){
        if(!CheckUser(loginUser)){
            throw new RuntimeException("Username or Password should not be empty or null");

        }
        for(User user: userRepository.findAll()){
            if(user.getUserName().equals(loginUser.getUserName())
                    && user.getPassword().equals(loginUser.getPassword())){
                System.out.println("deyesen oldu");
                return "deyesen oldu";
            }
        }
        System.out.println("deyesen olmadi");
        return "deyesen olmadi";
    }
    private boolean CheckUser(User user) {
        return !(user.getUserName() == null || user.getUserName().trim().isEmpty()
                || user.getPassword() == null || user.getPassword().trim().isEmpty());

    }
}
