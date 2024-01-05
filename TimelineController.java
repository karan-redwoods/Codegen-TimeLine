package com.redwoods.Timeline.controllers;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.redwoods.Timeline.dtos.TimelineResponseDto;
import com.redwoods.Timeline.dtos.TimelineResponseDto;
import com.redwoods.Timeline.models.Timeline;
import com.redwoods.Timeline.service.TimelineService;

@CrossOrigin
@RestController
@RequestMapping("/api/TimelineManagement")
public class TimelineController {

    private TimelineService timelineService;
    private ModelMapper modelMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(TimelineController.class);

    public TimelineController(TimelineService timelineService, ModelMapper modelMapper) {
        this.timelineService = timelineService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{timelineId}")
    public ResponseEntity<?> getTimeline(@PathVariable("timelineId") Long timelineId) {
        try {
            TimelineResponseDto TimelineresponsedtoDto = timelineService.getTimeline(timelineId);
            return new ResponseEntity<>(TimelineresponsedtoDto, HttpStatus.OK);
        } catch (Exception ex) {
            LOGGER.error("Error occurred while processing /TimelineManagement/" + timelineId, ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllTimelines() {
        try{
            List<TimelineResponseDto> TimelineresponsedtoDto = timelineService.getTimelines();
            if (TimelineresponsedtoDto != null) {
                return new ResponseEntity<>(TimelineresponsedtoDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No Timelines available!!", HttpStatus.OK);
            }
        } catch (Exception ex) {
            LOGGER.error("Error occurred while processing /TimelineManagement", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<String> addTimeline(@RequestBody TimelineResponseDto TimelineresponsedtoDto) {
        try{
            Timeline timeline = modelMapper.map(TimelineresponsedtoDto, Timeline.class);
            Long timelineId = timelineService.addTimeline(timeline);
            return new ResponseEntity<>(String.format("New Timeline added %s successfully.", timelineId), HttpStatus.CREATED);
        } catch (Exception ex) {
            LOGGER.error("Error occurred while processing /TimelineManagement", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{timelineId}")
    public ResponseEntity<?> updateTimeline(@PathVariable("timelineId") Long timelineId, @RequestBody TimelineResponseDto TimelineresponsedtoDto) {
        try{
            TimelineResponseDto TimelineresponsedtoDto = timelineService.updateTimeline(timelineId, TimelineresponsedtoDto);
            if (TimelineresponsedtoDto != null)
                return new ResponseEntity<>(TimelineresponsedtoDto, HttpStatus.CREATED);
            else
                return new ResponseEntity<>(String.format("No Timeline exist in the database with provided id %s.", timelineId), HttpStatus.OK);
        } catch (Exception ex) {
            LOGGER.error("Error occurred while processing /TimelineManagement/" + timelineId, ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{timelineId}")
    public ResponseEntity<String> deleteTimeline(@PathVariable("timelineId") Long timelineId) {
        String message = timelineService.deleteTimeline(timelineId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
