package com.springboot.rest.example.repository;

import com.springboot.rest.example.model.Episode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    List<Episode> findBySeasonSeasonId(Long seasonId);
    Optional<Episode> findByEpisodeIdAndSeasonSeasonId(Long episodeId, Long seasonId);
}