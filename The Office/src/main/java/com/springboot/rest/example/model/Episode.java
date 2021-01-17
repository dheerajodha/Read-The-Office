package com.springboot.rest.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.rest.example.repository.EpisodeRepository;
import com.springboot.rest.example.repository.SeasonRepository;

import java.util.Optional;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class Episode {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "episode_id")
	private Long episodeId;
	
	private String title;
		
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "season_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Season season;
	
	public Episode() {
		super();
	}
	
	public Episode(Long episodeId, String title) {
		this.episodeId = episodeId;
		this.title = title;
		//this.season = ;
	}
	
	public Long getEpisodeId() {
		return episodeId;
	}
	
	public void setEpisodeId(Long episodeId) {
		this.episodeId = episodeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Season getSeason() {
		return season;
	}
	
	public void setSeason(Season season) {
		this.season = season;
	}

}
