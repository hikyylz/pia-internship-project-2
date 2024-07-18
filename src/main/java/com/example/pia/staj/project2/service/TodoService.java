package com.example.pia.staj.project2.service;

import java.util.List;

import com.example.pia.staj.project2.exception.TodoCollectionException;
import com.example.pia.staj.project2.model.ToDoDTO;

import jakarta.validation.ConstraintViolationException;


public interface TodoService {
	
	public void createTodo(ToDoDTO todo) throws TodoCollectionException, ConstraintViolationException;
	
	public List<ToDoDTO> getAllTodos();
	
	public ToDoDTO getATodo(String id) throws TodoCollectionException;
	
	public void updateTodo(String id, ToDoDTO todo) throws TodoCollectionException; 
	
	public void deleteById(String id) throws TodoCollectionException; 

}
