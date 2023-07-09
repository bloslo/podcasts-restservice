package com.podcasts.restservice.repositories;

import com.podcasts.restservice.dtos.PodcastDto;
import com.podcasts.restservice.models.Podcast;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PodcastRepository extends JpaRepository<Podcast, Long> {
    Page<PodcastDto> findAllProjectedBy(Pageable pageable);
    Page<PodcastDto> findByNameContainingIgnoreCase(String name, Pageable pageable);
    PodcastDto findPodcastById(Long id);
}
