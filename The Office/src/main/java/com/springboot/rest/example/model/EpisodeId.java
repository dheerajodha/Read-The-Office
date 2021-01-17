package com.springboot.rest.example.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EpisodeId implements Serializable {
 
    @Column(name = "episode_id")
    private Long episodeId;
 
    @Column(name = "season_id")
    private Season season;
 
    public EpisodeId() {
    }
 
    public EpisodeId(Long episodeId, Season season) {
        this.episodeId = episodeId;
        this.season = season;
    }
 
    public Long getEpisodeId() {
        return episodeId;
    }
 
    public Season getSeason() {
        return season;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EpisodeId)) return false;
        EpisodeId that = (EpisodeId) o;
        return Objects.equals(getEpisodeId(), that.getEpisodeId()) &&
                Objects.equals(getSeason(), that.getSeason());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getEpisodeId(), getSeason());
    }
}