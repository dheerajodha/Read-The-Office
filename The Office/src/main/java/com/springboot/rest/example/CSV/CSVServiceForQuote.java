package com.springboot.rest.example.CSV;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.rest.example.model.Quote;
import com.springboot.rest.example.repository.QuoteRepository;

@Service
public class CSVServiceForQuote {
  @Autowired
  QuoteRepository repository;

  CSVHelper csvHelper = new CSVHelper();
  
  public void save(MultipartFile file) {
    try {
      List<Quote> quotes = csvHelper.csvToQuotes(file.getInputStream());
      repository.saveAll(quotes);
    } catch (IOException e) {
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
  }

  public ByteArrayInputStream load() {
    List<Quote> quotes = repository.findAll();

    ByteArrayInputStream in = csvHelper.quotesToCSV(quotes);
    return in;
  }

  public List<Quote> getAllQuotes() {
    return repository.findAll();
  }
}