package com.example.bahruztutorial;

import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<TodoModel, String> {  }
