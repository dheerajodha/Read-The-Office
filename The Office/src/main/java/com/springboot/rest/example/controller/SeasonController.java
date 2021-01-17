package com.springboot.rest.example.controller;

import java.util.List;
import com.springboot.rest.example.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.springboot.rest.example.model.Season;
import com.springboot.rest.example.repository.SeasonRepository;

@RestController
public class SeasonController {
	
	@Autowired
	private SeasonRepository seasonRepository;
	
	@GetMapping("/seasons")
	public List<Season> getAllSeasons() {
		return seasonRepository.findAll();
	}
	
	@PostMapping("/seasons")
	public Season addSeason(@RequestBody Season season) {
		return seasonRepository.save(season);
	}
	
	@PutMapping("/seasons/{seasonNo}")
	public Season updateSeason(@PathVariable Long seasonNo, @RequestBody Season seasonReq) {
		return seasonRepository.findById(seasonNo).map(season -> {
			season.setSeasonId(seasonReq.getSeasonId());
			season.setTotalEpisodes(seasonReq.getTotalEpisodes());
			season.setAiredOn(seasonReq.getAiredOn());
			return seasonRepository.save(season);
		}).orElseThrow(() -> new ResourceNotFoundException("SeasonNo " + seasonNo + " not found"));
	}
	
	@DeleteMapping("/seasons/{seasonNo}")
	public ResponseEntity<?> deletePost(@PathVariable Long seasonNo) {
		return seasonRepository.findById(seasonNo).map(season -> {
			seasonRepository.delete(season);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("SeasonNo " + seasonNo + " not found"));
	}
	
}
