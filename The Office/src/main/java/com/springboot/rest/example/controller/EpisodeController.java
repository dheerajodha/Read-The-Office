package com.springboot.rest.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rest.example.exception.ResourceNotFoundException;
import com.springboot.rest.example.model.Episode;
import com.springboot.rest.example.repository.EpisodeRepository;
import com.springboot.rest.example.repository.SeasonRepository;

@RestController
public class EpisodeController {
	@Autowired
	EpisodeRepository episodeRepository;
	
	@Autowired
	SeasonRepository seasonRepository;
	
	@GetMapping("/seasons/{seasonId}/episodes")
	public List<Episode> getAllEpisodesBySeasonId(@PathVariable (value = "seasonId") Long seasonId) {
		return episodeRepository.findBySeasonSeasonId(seasonId);
	}
	
	@PostMapping("/seasons/{seasonId}/episodes")
	public Episode addEpisode(@PathVariable (value = "seasonId") Long seasonId, @RequestBody Episode episode) {
		return seasonRepository.findById(seasonId).map(season -> {
			episode.setSeason(season);
			return episodeRepository.save(episode);
		}).orElseThrow(() -> new ResourceNotFoundException("SeasonId " + seasonId + " not found"));
	}
	
	@PutMapping("/seasons/{seasonId}/episodes/{episodeId}")
	public Episode updateEpisode(@PathVariable (value = "seasonId") Long seasonId,
									@PathVariable (value = "episodeId") Long episodeId,
									@RequestBody Episode episodeRequest) {
		if (!seasonRepository.existsById(seasonId)) {
			throw new ResourceNotFoundException("SeasonId " + seasonId + " not found");
		}
		
		return episodeRepository.findById(episodeId).map(episode -> {
			episode.setTitle(episodeRequest.getTitle());
			return episodeRepository.save(episode);
		}).orElseThrow(() -> new ResourceNotFoundException("EpisodeId " + episodeId + " not found"));
	}
	
	@DeleteMapping("/seasons/{seasonId}/episodes/{episodeId}")
	public ResponseEntity<?> deleteEpisode(@PathVariable (value = "seasonId") Long seasonId,
											@PathVariable (value = "episodeId") Long episodeId) {
		return episodeRepository.findByEpisodeIdAndSeasonSeasonId(episodeId, seasonId).map(episode -> {
			episodeRepository.delete(episode);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Episode not found with id " + episodeId + " and seasonId " + seasonId));
	}
	
}
