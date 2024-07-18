package com.example.pia.staj.project2.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pia.staj.project2.exception.TodoCollectionException;
import com.example.pia.staj.project2.model.ToDoDTO;
import com.example.pia.staj.project2.repository.ToDoRespository;

import jakarta.validation.ConstraintViolationException;

@Service
public class TodoServiceImpl implements TodoService {
	// bu service class ı repo yu autowired ile oluşturması gerekmekteymiş.
	
	@Autowired
	private ToDoRespository todoRepo;
	

	@Override
	public void createTodo(ToDoDTO todo) throws TodoCollectionException, ConstraintViolationException {
		// repo ya kaydetmek için bir todo oluşturacağım ve eğer halihazırda varsa hata atacağım.
		Optional<ToDoDTO> todoOptional = todoRepo.findByTodo(todo.getToDo());
		
		if(todoOptional.isPresent()) {
			throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExcists());
		}else {
			todo.setCreatedAt(new Date(System.currentTimeMillis()));
			todoRepo.save(todo);
		}
		
	}


	@Override
	public List<ToDoDTO> getAllTodos() {
		List<ToDoDTO> todos = todoRepo.findAll();
		if(todos.size() > 0) {
			return todos;
		}
		else {
			return new ArrayList<ToDoDTO>();
		}
	}

}
