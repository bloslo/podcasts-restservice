package com.podcasts.restservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class CreateEpisodeDto {
    @NotBlank(message = "Title cannot be blank.")
    @Size(min = 5, max = 100, message = "The episode title must be between 5 and 48 characters long.")
    private String title;
    @NotNull(message = "Published date must be specified.")
    private LocalDate published;
    @NotNull(message = "Podcast id must be specified.")
    private Long podcastId;

    public CreateEpisodeDto(String title, LocalDate published, Long podcastId) {
        this.title = title;
        this.published = published;
        this.podcastId = podcastId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getPublished() {
        return published;
    }

    public void setPublished(LocalDate published) {
        this.published = published;
    }

    public Long getPodcastId() {
        return podcastId;
    }

    public void setPodcastId(Long podcastId) {
        this.podcastId = podcastId;
    }
}
