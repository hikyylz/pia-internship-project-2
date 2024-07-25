package com.example.pia.staj.project2.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



//-------------


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="todos")
public class ToDoDTO {
	
	@Id
	private String id;
	
	@NotNull(message = "todo cannot be null")
	private String ToDo;
	
	@NotNull(message = "description cannot be null")
	private String description;
	
	@NotNull(message = "completed cannot be null")
	private Boolean completed;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getToDo() {
		return ToDo;
	}
	public void setToDo(String toDo) {
		ToDo = toDo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getCompleted() {
		return completed;
	}
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
