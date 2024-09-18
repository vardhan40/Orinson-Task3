package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Book;
import com.example.demo.Repository.BookRepository;

@Service
public class BookService {
	@Autowired
	BookRepository bookRepo;

	public Book saveOrUpdate(Book book) {
		return bookRepo.save(book);
	}

	public List<Book> getAllBooks() {
		return bookRepo.findAll();
	}

	public Optional<Book> getBookById(int id) {
		return bookRepo.findById(id);
	}

	public void deleteBookById(int id) {
		bookRepo.findById(id);
		bookRepo.deleteById(id);
	}

}
