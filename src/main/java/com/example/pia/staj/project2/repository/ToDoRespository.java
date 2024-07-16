package com.example.pia.staj.project2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.pia.staj.project2.model.ToDoDTO;


// bu file da mongoDB ile etkileşmemiz sağlayacak olan kodlar bulunacaktır.

@Repository
public interface ToDoRespository extends MongoRepository<ToDoDTO, String> {

}
