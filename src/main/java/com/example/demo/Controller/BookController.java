package com.example.demo.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Book;
import com.example.demo.Exception.BookExceptionHandler;
import com.example.demo.Exception.ErrorResponse;
import com.example.demo.Service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {
	@Autowired
	BookService bookService;

	@PostMapping()
	public Book addBook(@Valid @RequestBody Book book) {
		return bookService.saveOrUpdate(book);
	}

	@GetMapping()
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> books = bookService.getAllBooks();
		return ResponseEntity.ok(books);
	}

	@GetMapping("/{id}")
	public Book getBookById(@PathVariable int id) {
		return bookService.getBookById(id)
				.orElseThrow(() -> new BookExceptionHandler("Book is not present with this ID"));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Book> updatebook(@PathVariable int id, @RequestBody Book book) {
		Book updateBook = bookService.getBookById(id)
				.orElseThrow(() -> new BookExceptionHandler("Book is not present with this ID"));
		book.setId(id);
		Book addBook = bookService.saveOrUpdate(book);
		return ResponseEntity.status(HttpStatus.CREATED).body(addBook);
	}

	@DeleteMapping("/{id}")
	public void deletebook(@PathVariable int id) {
		bookService.getBookById(id).orElseThrow(() -> new BookExceptionHandler("Book is not present with this ID"));
		bookService.deleteBookById(id);
	}

	@ExceptionHandler(BookExceptionHandler.class)
	public ResponseEntity<Object> handlerCustomException(BookExceptionHandler ex) {
		ErrorResponse response = new ErrorResponse();
		response.setMessage(ex.getMessage());
		response.setTimeStamp(LocalDateTime.now());
		response.setErrorCode(HttpStatus.NOT_FOUND);

		return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
	}

}
