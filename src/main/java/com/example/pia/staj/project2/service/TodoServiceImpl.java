package com.example.pia.staj.project2.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.hibernate.validator.internal.util.logging.formatter.CollectionOfObjectsToStringFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


	@Override
	public ToDoDTO getATodo(String id) throws TodoCollectionException {
		
		Optional<ToDoDTO> optinalTodo = todoRepo.findById(id);
		
		if(optinalTodo.isPresent()) {
			return optinalTodo.get();
		}else {
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		}
	}


	@Override
	public void updateTodo(String id, ToDoDTO todo) throws TodoCollectionException {
		Optional<ToDoDTO> todowithIdOptional = todoRepo.findById(id);
		Optional<ToDoDTO> todowithSamenameOptional = todoRepo.findByTodo(todo.getToDo());
		
		if(todowithIdOptional.isPresent()) {
			if (todowithSamenameOptional.isPresent() && !todowithSamenameOptional.get().getId().equals(id)) {
				// aynı id ile aynı şeyi kaydetmeye çalışıyorsa kullanıcı bunu engellememiz lazım.
				throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExcists());
			}
			
			ToDoDTO todoToUpdate = todowithIdOptional.get();
			
			todoToUpdate.setToDo(todo.getToDo());
			todoToUpdate.setDescription(todo.getDescription());
			todoToUpdate.setCompleted(todo.getCompleted());
			todoToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
			todoRepo.save(todoToUpdate);
			
		}else {
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		}
		
	}

	

	@Override
	public void deleteById(String id) throws TodoCollectionException {
		Optional<ToDoDTO> todoopOptional = todoRepo.findById(id);
		if(todoopOptional.isPresent()) {
			todoRepo.deleteById(id);
			
		}else {
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		}
		
		
	}

	

}
