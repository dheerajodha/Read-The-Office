package com.springboot.rest.example.repository;

import com.springboot.rest.example.model.Episode;
import com.springboot.rest.example.model.Season;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {
    Season findBySeasonId(Long seasonId);
}