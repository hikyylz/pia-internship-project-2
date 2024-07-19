package com.example.pia.staj.project2.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.ReturnedType;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.pia.staj.project2.exception.TodoCollectionException;
import com.example.pia.staj.project2.model.ToDoDTO;
import com.example.pia.staj.project2.repository.ToDoRespository;
import com.example.pia.staj.project2.service.TodoService;
import com.example.pia.staj.project2.service.TodoServiceImpl;

import jakarta.validation.ConstraintViolationException;


// import edilenler sırasıyla;
// java ile ilgili, projeyle ilgili, benim yazdığım kodlarla ilgili sırasıyla import edilir.

// Controller class ında DB içerisindeki işlemlerin yapıldığı kod bloklarının olduğu file dır.
// contaroller class ında çok fazla try-catch yapısı kullanılır.


@RestController
public class Todocontroller {

	@Autowired
	private ToDoRespository TodoRepo; 
	
	@Autowired
	private TodoService TodoServ; 
	
	
	
	
	@GetMapping("/todos")
	public ResponseEntity<?> getAllTodos(){
		// bu method yapılacaklar listebisini repo dan dondürecek olan method. 
		
		List<ToDoDTO> todos = TodoServ.getAllTodos();
		
		return new ResponseEntity<>(todos, todos.size()>0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
		
	}
	
	
	
	@PostMapping("/todos")
	public ResponseEntity<?> createTodo(@RequestBody ToDoDTO todo){
		// bu method bir todo yaratıyor ve onu todo listesine kaydediyor. Eğer kaydedilme olmuyorsa internel server error veriyor.
		// işlemi yapmaya çalıştırrken problem çıkarsa yönlendirme işlemleri de burada yapılıyor. 
		
		try {
			TodoServ.createTodo(todo);   // controller class ından servis deki methodları çalıştıracapım sadece ihtiyacım olursa.
			return new ResponseEntity<ToDoDTO>(todo, HttpStatus.OK);
			
		}catch(ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		}catch (TodoCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	
	
	@GetMapping("/todos/{id}")  // parantez içerisindeki değeri ben request i isterken çalıştıracağım anlamına geliyor.
	public ResponseEntity<?> getSingleToDo(@PathVariable("id") String id ){
		// bu method DB den id e göre bir get request i yapıyor.
		
		try {
			return new ResponseEntity<>(TodoServ.getATodo(id), HttpStatus.OK);
			
		} catch (TodoCollectionException  e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	// parametrelere anotationların özel anlamları vardır:
	// @RequestBody : input olarak alınması gerekiyor.
	// @PathVariable : methodun çalışması için gerekli inputlardan biri.
	
	
	
	
	
	@PutMapping("/todos/{id}")  // parantez içerisindeki değeri ben request i isterken çalıştıracağım anlamına geliyor.
	public ResponseEntity<?> updateById(@PathVariable("id") String id , @RequestBody ToDoDTO todo){
		// bu method DB den id e göre bir get request i yapıyor.
		// belli id e sahip todo yu güncellememe yararacak bu method.
		
		
		try {
			TodoServ.updateTodo(id, todo);
			return new ResponseEntity<>("todo updated with id "+id, HttpStatus.OK);
			
		} catch (TodoCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
			
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		
		}
	}
	
	
	@DeleteMapping("/todos/{id}")  // bu anotation lar http request türüne göre hangi methodun çalışmasını seçen yapı.
	public ResponseEntity<?> deleteById(@PathVariable("id") String id){
		try {
			TodoServ.deleteById(id);
			return new ResponseEntity<>("succesfully deleted id "+id , HttpStatus.OK);
			
		} catch (TodoCollectionException e) {
			return new ResponseEntity<>(e.getMessage() , HttpStatus.NOT_FOUND);
			
		}
	}
	
	
	
}
















