package com.example.pia.staj.project2.service;

import com.example.pia.staj.project2.exception.TodoCollectionException;
import com.example.pia.staj.project2.model.ToDoDTO;

import jakarta.validation.ConstraintViolationException;

public interface TodoService {
	
	public void createTodo(ToDoDTO todo) throws TodoCollectionException, ConstraintViolationException;

}