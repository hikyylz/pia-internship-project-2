package com.example.pia.staj.project2.exception;

public class TodoCollectionException extends Exception {
	// uygulamamda exception ile karşılaşıldığınd nasıl tepki vereceğimi ayarlıyorum.

	private static final long serialVersionUID = 1L;
	
	public TodoCollectionException(String message) {
		super(message);
	}
	
	public static String NotFoundException(String id) {
		return "todo with " + id + " not found! ";
	}
	
	public static String TodoAlreadyExcists() {
		return "todo with given name is allready excists.";
	}
	

}
