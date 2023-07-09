package com.podcasts.restservice.repositories;

import com.podcasts.restservice.dtos.EpisodeDto;
import com.podcasts.restservice.models.Episode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    Page<EpisodeDto> findAllEpisodesByPodcastName(String podcastName, Pageable pageable);
}
