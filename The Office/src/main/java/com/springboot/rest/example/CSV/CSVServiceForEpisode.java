package com.springboot.rest.example.CSV;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.rest.example.model.Episode;
import com.springboot.rest.example.repository.EpisodeRepository;

@Service
public class CSVServiceForEpisode {
	
	@Autowired
	EpisodeRepository episodeRepository;
	
	CSVHelper csvHelper = new CSVHelper();
	
	public void save(MultipartFile file) {
		try {
			System.out.println("above");
			List<Episode> episodes = csvHelper.csvToEpisodes(file.getInputStream());
			System.out.println("below");
			episodeRepository.saveAll(episodes);
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}
	
	public ByteArrayInputStream load() {
		List<Episode> episodes = episodeRepository.findAll();
		
		ByteArrayInputStream in = csvHelper.episodesToCSV(episodes);
		return in;
	}
	
	public List<Episode> getAllEpisodes(Long seasonId) {
		return episodeRepository.findBySeasonSeasonId(seasonId);
	}
	
}
