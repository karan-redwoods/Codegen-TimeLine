package com.redwoods.Timeline.service;

import java.util.List;

import com.redwoods.Timeline.dtos.TimelineResponseDto;
import com.redwoods.Timeline.dtos.TimelineResponseDto;
import com.redwoods.Timeline.models.Timeline;

public interface TimelineInterface {

    TimelineResponseDto getTimeline(Long timelineId);

    List<TimelineResponseDto> getTimelines();

    String deleteTimeline(Long timelineId);

    TimelineResponseDto updateTimeline(Long timelineId, TimelineResponseDto timelineRequest);

    Long addTimeline(Timeline timeline);

}
