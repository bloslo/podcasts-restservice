package com.podcasts.restservice.exceptions;

public class PodcastNotFoundException extends RuntimeException {
    public PodcastNotFoundException() {
        super();
    }

    public PodcastNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PodcastNotFoundException(String message) {
        super(message);
    }

    public PodcastNotFoundException(Throwable cause) {
        super(cause);
    }
}
