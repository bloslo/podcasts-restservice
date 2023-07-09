package com.podcasts.restservice.exceptions;

public class PodcastNameAlreadyExists extends RuntimeException {
    public PodcastNameAlreadyExists() {
        super();
    }

    public PodcastNameAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

    public PodcastNameAlreadyExists(String message) {
        super(message);
    }

    public PodcastNameAlreadyExists(Throwable cause) {
        super(cause);
    }
}
