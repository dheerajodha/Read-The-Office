package com.springboot.rest.example.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Season {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seasonId;
	

	@OneToMany(targetEntity = Episode.class, mappedBy="season")
	private List<Episode> episodes = new ArrayList<Episode>();

	private int totalEpisodes;
	       
	private String airedOn;
	
	public Season() {
		super();
	}

	public Season(Long seasonId, int totalEpisodes, String airedOn) {
		super();
		this.seasonId = seasonId;
		this.totalEpisodes = totalEpisodes;
		this.airedOn = airedOn;
	}
	
	public List<Episode> getEpisodes() { 
		return episodes; 
	}
	public void setEpisodes(List<Episode> episodes) { 
		this.episodes = episodes; 
	}	
	public Long getSeasonId() {
		return seasonId;
	}
	public void setSeasonId(Long seasonId) {
		this.seasonId = seasonId;
	}
	public int getTotalEpisodes() {
		return totalEpisodes;
	}
	public void setTotalEpisodes(int totalEpisodes) {
		this.totalEpisodes = totalEpisodes;
	}
	public String getAiredOn() {
		return airedOn;
	}
	public void setAiredOn(String airedOn) {
		this.airedOn = airedOn;
	}
}
