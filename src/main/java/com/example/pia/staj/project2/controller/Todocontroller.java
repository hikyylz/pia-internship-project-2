package com.example.pia.staj.project2.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pia.staj.project2.model.ToDoDTO;
import com.example.pia.staj.project2.repository.ToDoRespository;


// import edilenler sırasıyla;
// java ile ilgili, projeyle ilgili, benim yazdığım kodlarla ilgili sırasıyla import edilir.

// Controller class ında DB içerisindeki işlemlerin yapıldığı kod bloklarının olduğu file dır.


@RestController
public class Todocontroller {

	@Autowired
	private ToDoRespository TodoRepo; 
	
	@GetMapping("/todos")
	public ResponseEntity<?> getAllTodos(){
		// bu method yapılacaklar listebisini repo dan dondürecek olan method. 
		
		List<ToDoDTO> todos = TodoRepo.findAll();
		if(todos.size() > 0) {
			return new ResponseEntity<List<ToDoDTO>>(todos, HttpStatus.OK);
		}else {
			return new ResponseEntity<>("no todos available", HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping("/todos")
	public ResponseEntity<?> createTodo(@RequestBody ToDoDTO todo){
		// bu method bir todo yaratıyor ve onu todo listesine kaydediyor. Eğer kaydedilme olmuyorsa internel server error veriyor.
		
		try {
			todo.setCreatedAt(new Date(System.currentTimeMillis()));
			TodoRepo.save(todo);
			return new ResponseEntity<ToDoDTO>(todo, HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/todos/{id}")  // parantez içerisindeki değeri ben request i isterken çalıştıracağım anlamına geliyor.
	public ResponseEntity<?> getSingleToDo(@PathVariable("id") String id ){
		// bu method DB den id e göre bir get request i yapıyor.
		
		Optional<ToDoDTO> todoOptional = TodoRepo.findById(id);
		
		if (todoOptional.isPresent()) {
			return new ResponseEntity<>(todoOptional.get(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>("todo not found by id "+id , HttpStatus.NOT_FOUND);
		}
	}
	
	
	// parametrelere anotationların özel anlamları vardır:
	// @RequestBody : input olarak alınması gerekiyor.
	// @PathVariable : methodun çalışması için gerekli inputlardan biri.
	
	
	
	@PutMapping("/todos/{id}")  // parantez içerisindeki değeri ben request i isterken çalıştıracağım anlamına geliyor.
	public ResponseEntity<?> updateById(@PathVariable("id") String id , @RequestBody ToDoDTO todo){
		// bu method DB den id e göre bir get request i yapıyor.
		// belli id e sahip todo yu güncellememe yararacak bu method.
		
		
		Optional<ToDoDTO> todoOptional = TodoRepo.findById(id);
		
		if (todoOptional.isPresent()) {
			ToDoDTO todosave = todoOptional.get();	
			todosave.setCompleted(todo.getCompleted() != null ? todo.getCompleted() : todosave.getCompleted());
			todosave.setToDo(todo.getToDo() != null ? todo.getToDo() : todosave.getToDo());
			todosave.setDescription(todo.getDescription() != null ? todo.getDescription() : todosave.getDescription());
			todosave.setUpdatedAt(new Date(System.currentTimeMillis()));
			TodoRepo.save(todosave);
			return new ResponseEntity<>(todosave, HttpStatus.OK);
			
			
		}else {
			return new ResponseEntity<>("todo not found by id "+id , HttpStatus.NOT_FOUND);
		}
	}
	
	
	@DeleteMapping("/todos/{id}")  // bu anotation lar http request türüne göre hangi methodun çalışmasını seçen yapı.
	public ResponseEntity<?> deleteById(@PathVariable("id") String id){
		try {
			TodoRepo.deleteById(id);
			return new ResponseEntity<>("succesfult deleted id "+id , HttpStatus.OK);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage() , HttpStatus.NOT_FOUND);
			
		}
	}
	
	
	
}
















