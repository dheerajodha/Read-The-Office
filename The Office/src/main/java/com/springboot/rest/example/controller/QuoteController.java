package com.springboot.rest.example.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.rest.example.exception.QuoteNotFoundException;
import com.springboot.rest.example.model.Quote;
import com.springboot.rest.example.repository.QuoteRepository;

@RestController
public class QuoteController {

	@Autowired
	private QuoteRepository quoteRepository;

	@GetMapping("/quotess")
	public List<Quote> retrieveAllStudents() {
		return quoteRepository.findAll();
	}

	@GetMapping("/quotess/{id}")
	public Quote retrieveStudent(@PathVariable long id) {
		Optional<Quote> quote = quoteRepository.findById(id);

		if (!quote.isPresent())
			throw new QuoteNotFoundException("id-" + id);

		return quote.get();
	}

	@DeleteMapping("/quotess/{id}")
	public void deleteQuote(@PathVariable long id) {
		quoteRepository.deleteById(id);
	}

	@PostMapping("/quotess")
	public ResponseEntity<Object> createQuote(@RequestBody Quote quote) {
		Quote savedQuote = quoteRepository.save(quote);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedQuote.getId()).toUri();

		return ResponseEntity.created(location).build();

	}
	
	@PutMapping("/quotess/{id}")
	public ResponseEntity<Object> updateQuotes(@RequestBody Quote quote, @PathVariable long id) {

		Optional<Quote> quoteOptional = quoteRepository.findById(id);

		if (!quoteOptional.isPresent())
			return ResponseEntity.notFound().build();

		quote.setId(id);
		
		quoteRepository.save(quote);

		return ResponseEntity.noContent().build();
	}
}