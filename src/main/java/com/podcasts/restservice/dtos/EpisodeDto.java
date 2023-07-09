package com.podcasts.restservice.dtos;

import java.time.LocalDate;

public interface EpisodeDto {
    String getTitle();
    LocalDate getPublished();

}
