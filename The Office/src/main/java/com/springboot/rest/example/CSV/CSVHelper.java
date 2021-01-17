package com.springboot.rest.example.CSV;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.rest.example.exception.ResourceNotFoundException;
import com.springboot.rest.example.model.Episode;
import com.springboot.rest.example.model.Quote;
import com.springboot.rest.example.model.Season;
import com.springboot.rest.example.model.Student;
import com.springboot.rest.example.repository.SeasonRepository;


public class CSVHelper {
  public static String TYPE = "text/csv";
  static String[] HEADERs = { "Id", "firstName", "lastName", "content" };
  
  @Autowired
  private SeasonRepository seasonRepository;

  public static boolean hasCSVFormat(MultipartFile file) {
    if (TYPE.equals(file.getContentType())
    		|| file.getContentType().equals("application/vnd.ms-excel")) {
      return true;
    }

    return false;
  }
  
  public List<Episode> csvToEpisodes(InputStream is) {	  
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader,
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
      System.out.println("hiiiiiii");
      List<Episode> episodeList = new ArrayList<Episode>();

      Iterable<CSVRecord> csvRecords = csvParser.getRecords();
      System.out.println("yoo");

      for (CSVRecord csvRecord : csvRecords) {
    	  System.out.println(Long.parseLong(csvRecord.get("episodeId")) + " : " + csvRecord.get("title") + " : " + Long.parseLong(csvRecord.get("seasonId")));
    	  
    	  Episode episode = new Episode(
              Long.parseLong(csvRecord.get("episodeId")),
              csvRecord.get("title")
            );
    	  
    	  Long seasonId = Long.parseLong(csvRecord.get("seasonId"));
    	  
    	  System.out.println(seasonId);
    	  
    	  Season season = seasonRepository.findBySeasonId((long) seasonId);

    	  episode.setSeason(season);
    	  
    	  //episode.getSeason().setSeasonId(seasonId);
    	  
    	  System.out.println(episode.getSeason());
    	  System.out.println(episode.getSeason().getTotalEpisodes());
    	  
    	  episodeList.add(episode);
      }

      return episodeList;
    } catch (IOException e) {
  	  System.out.println("ii");
      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }
  }
  
  public List<Season> csvToSeasons(InputStream is) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader,
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

      List<Season> seasonList = new ArrayList<>();

      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

      for (CSVRecord csvRecord : csvRecords) {
    	  Season season = new Season(
              Long.parseLong(csvRecord.get("seasonId")),
              Integer.parseInt(csvRecord.get("totalEpisodes")),
              csvRecord.get("airedOn")
            );

    	  seasonList.add(season);
      }

      return seasonList;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }
  }

  public List<Quote> csvToQuotes(InputStream is) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader,
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

      List<Quote> quoteList = new ArrayList<>();

      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

      for (CSVRecord csvRecord : csvRecords) {
    	  Quote quote = new Quote(
              Long.parseLong(csvRecord.get("Id")),
              csvRecord.get("firstName"),
              csvRecord.get("lastName"),
              csvRecord.get("content")
            );

    	  quoteList.add(quote);
      }

      return quoteList;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }
  }
  
  public List<Student> csvToStudents(InputStream is) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader,
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

      List<Student> studentList = new ArrayList<>();

      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

      for (CSVRecord csvRecord : csvRecords) {
    	  Student student = new Student(
              Long.parseLong(csvRecord.get("Id")),
              csvRecord.get("Name"),
              csvRecord.get("Passport_Number")
            );

    	  studentList.add(student);
      }

      return studentList;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }
  }

  public ByteArrayInputStream studentsToCSV(List<Student> studentList) {
    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
      for (Student student : studentList) {
        List<String> data = Arrays.asList(
              String.valueOf(student.getId()),
              student.getStudentName(),
              student.getPassportNumber()
            );

        csvPrinter.printRecord(data);
      }

      csvPrinter.flush();
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
    }
  }

  public ByteArrayInputStream quotesToCSV(List<Quote> quoteList) {
    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
      for (Quote quote : quoteList) {
        List<String> data = Arrays.asList(
              String.valueOf(quote.getId()),
              quote.getFirstName(),
              quote.getLastName(),
              quote.getContent() 
            );

        csvPrinter.printRecord(data);
      }

      csvPrinter.flush();
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
    }
  }
  
  public ByteArrayInputStream seasonsToCSV(List<Season> seasonList) {
    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
      for (Season season : seasonList) {
        List<String> data = Arrays.asList(
              String.valueOf(season.getSeasonId()),
              String.valueOf(season.getTotalEpisodes()),
              season.getAiredOn() 
            );

        csvPrinter.printRecord(data);
      }

      csvPrinter.flush();
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
    }
  }
  
  public ByteArrayInputStream episodesToCSV(List<Episode> episodeList) {
    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
      for (Episode episode : episodeList) {
        List<String> data = Arrays.asList(
        	  String.valueOf(episode.getEpisodeId()),
              String.valueOf(episode.getTitle()),
              String.valueOf(episode.getSeason().getSeasonId()) 
            );

        csvPrinter.printRecord(data);
      }

      csvPrinter.flush();
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
    }
  }
  
}