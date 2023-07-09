package com.podcasts.restservice.services;

import com.podcasts.restservice.dtos.CreateEpisodeDto;
import com.podcasts.restservice.dtos.EpisodeDto;
import com.podcasts.restservice.dtos.PodcastDto;
import com.podcasts.restservice.dtos.CreatePodcastDto;
import com.podcasts.restservice.exceptions.PodcastNameAlreadyExists;
import com.podcasts.restservice.exceptions.PodcastNotFoundException;
import com.podcasts.restservice.models.Episode;
import com.podcasts.restservice.models.Podcast;
import com.podcasts.restservice.repositories.EpisodeRepository;
import com.podcasts.restservice.repositories.PodcastRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PodcastService {
    private static final Logger log = LoggerFactory.getLogger(PodcastService.class);

    private final PodcastRepository podcastRepository;
    private final EpisodeRepository episodeRepository;

    PodcastService(PodcastRepository podcastRepository, EpisodeRepository episodeRepository) {
        this.podcastRepository = podcastRepository;
        this.episodeRepository = episodeRepository;
    }

    public List<PodcastDto> getAllPodcasts(Pageable pageable) {
        return this.podcastRepository.findAllProjectedBy(pageable).getContent();
    }

    public PodcastDto getPodcastById(Long id) {
        return this.podcastRepository.findPodcastById(id);
    }

    public CreatePodcastDto createPodcast(CreatePodcastDto createPodcastDto) {
        log.info("Create podcast");
        List<PodcastDto> podcasts = this.podcastRepository
                .findByNameContainingIgnoreCase(createPodcastDto.getName(), Pageable.ofSize(10))
                .getContent();
        if (!podcasts.isEmpty()) {
            throw new PodcastNameAlreadyExists(
                    String.format("There is already a podcast with the name %s", createPodcastDto.getName()));
        }

        Podcast podcast = new Podcast(createPodcastDto.getName(), createPodcastDto.getPublisher());
        podcast = this.podcastRepository.save(podcast);
        CreatePodcastDto createdPodcast = new CreatePodcastDto();
        createdPodcast.setName(podcast.getName());
        createdPodcast.setPublisher(podcast.getPublisher());
        return createPodcastDto;
    }

    public PodcastDto getPodcastByName(String podcastName) {
        List<PodcastDto> podcasts = this.podcastRepository
                .findByNameContainingIgnoreCase(podcastName, Pageable.ofSize(10))
                .getContent();

        if (podcasts.isEmpty()) {
            return null;
        }

        return podcasts.get(0);
    }

    public List<EpisodeDto> getAllEpisodesForPodcast(String podcastName, Pageable pageable) {
        return this.episodeRepository.findAllEpisodesByPodcastName(podcastName, pageable).getContent();
    }

    public CreateEpisodeDto createEpisode(CreateEpisodeDto createEpisodeDto) {
        Optional<Podcast> podcast = this.podcastRepository.findById(createEpisodeDto.getPodcastId());
        if (podcast.isEmpty()) {
            throw new PodcastNotFoundException("There is no podcast with an id " + createEpisodeDto.getPodcastId());
        }

        Episode episode = new Episode(createEpisodeDto.getTitle(), createEpisodeDto.getPublished(), podcast.get());
        episode = this.episodeRepository.save(episode);
        return new CreateEpisodeDto(
                episode.getTitle(),
                episode.getPublished(),
                episode.getPodcast().getId());
    }
}
