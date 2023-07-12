package com.podcasts.restservice.controllers;

import com.podcasts.restservice.dtos.*;
import com.podcasts.restservice.exceptions.PodcastNameAlreadyExists;
import com.podcasts.restservice.exceptions.PodcastNotFoundException;
import com.podcasts.restservice.services.PodcastService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class PodcastController {

    private static final Logger log = LoggerFactory.getLogger(PodcastController.class);

    private final PodcastService podcastService;

    PodcastController(PodcastService podcastService) {
        this.podcastService = podcastService;
    }

    @GetMapping("/podcasts")
    List<PodcastDto> getAllPodcasts(Pageable pageable) {
        return podcastService.getAllPodcasts(pageable);
    }

    @GetMapping("/podcast/{id}")
    PodcastDto getPodcastById(@PathVariable Long id) {
        return podcastService.getPodcastById(id);
    }

    @GetMapping("/podcast")
    PodcastDto getPodcastByName(@RequestParam String podcastName) {
        return podcastService.getPodcastByName(podcastName);
    }

    @PostMapping("/podcast")
    CreatePodcastDto createPodcast(@Valid @RequestBody CreatePodcastDto createPodcastDto) {
        return podcastService.createPodcast(createPodcastDto);
    }

    @GetMapping("/podcast/episodes")
    List<EpisodeDto> getAllEpisodesForPodcast(@RequestParam(name = "podcastName") String podcastName,
                                              Pageable pageable) {
        return podcastService.getAllEpisodesForPodcast(podcastName, pageable);
    }

    @PostMapping("/podcast/episode")
    CreateEpisodeDto createEpisode(@Valid @RequestBody CreateEpisodeDto createEpisodeDto) {
        return podcastService.createEpisode(createEpisodeDto);
    }

    @ExceptionHandler({PodcastNameAlreadyExists.class, PodcastNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handlePodcastNameAlreadyExistsException(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, List<String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return getErrorsMap(errors);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
