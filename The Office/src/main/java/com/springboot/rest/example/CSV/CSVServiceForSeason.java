package com.springboot.rest.example.CSV;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.rest.example.model.Season;
import com.springboot.rest.example.repository.SeasonRepository;

@Service
public class CSVServiceForSeason {
  @Autowired
  SeasonRepository repository;
  
  CSVHelper csvHelper = new CSVHelper();

  public void save(MultipartFile file) {
    try {
      List<Season> seasons = csvHelper.csvToSeasons(file.getInputStream());
      repository.saveAll(seasons);
    } catch (IOException e) {
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
  }

  public ByteArrayInputStream load() {
    List<Season> seasons = repository.findAll();

    ByteArrayInputStream in = csvHelper.seasonsToCSV(seasons);
    return in;
  }

  public List<Season> getAllSeasons() {
    return repository.findAll();
  }
}