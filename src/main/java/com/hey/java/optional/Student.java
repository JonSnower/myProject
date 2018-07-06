package com.hey.java.optional;

import java.util.Optional;

public class Student {
	private String name;
	
	private Optional<Book> book;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Optional<Book> getBook() {
		return book;
	}

	public void setBook(Optional<Book> book) {
		this.book = book;
	}
	
	
}
