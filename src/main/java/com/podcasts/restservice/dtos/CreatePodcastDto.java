package com.podcasts.restservice.dtos;

import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

public class CreatePodcastDto {

    @NotBlank(message = "Name cannot be blank.")
    @Size(min = 3, max = 32, message = "The podcast name must be between 3 and 32 characters long.")
    private String name;
    @NotBlank(message = "Publisher cannot be blank.")
    private String publisher;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
