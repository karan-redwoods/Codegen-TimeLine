package com.redwoods.Timeline.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.redwoods.Timeline.dtos.TimelineResponseDto;
import com.redwoods.Timeline.dtos.TimelineRequestDto;
import com.redwoods.Timeline.exceptions.NotFoundException;
import com.redwoods.Timeline.models.Address;
import com.redwoods.Timeline.models.Timeline;
import com.redwoods.Timeline.models.TimelineContact;
import com.redwoods.Timeline.models.TimelineData;
import com.redwoods.Timeline.repos.TimelineRepository;
import TimelineService.service.TimelineServiceImpl;

@Service
public class TimelineServiceImpl implements TimelineServiceImpl {

   private TimelineRepository TimelineRepository;
   private ModelMapper modelMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(TimelineServiceImpl.class);

    public TimelineServiceImpl(TimelineRepository TimelineRepository, ModelMapper modelMapper) {
        this.TimelineRepository = TimelineRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TimelineResponseDto getTimeline(Long TimelineId) throws NotFoundException {
        try {
            Optional<Timeline> optionalTimeline = TimelineRepository.findById(TimelineId);
            if (optionalTimeline.isEmpty()) {
                throw new NotFoundException("Timeline Doesn't exist.");
            }

            return modelMapper.map(optionalTimeline.get(), TimelineResponseDto.class);
        } catch (Exception ex) {
            LOGGER.error("Error occurred while processing getTimeline", ex);
            throw new RuntimeException("Error occurred while processing getTimeline", ex);
        }
    }

    @Override
    public List<TimelineResponseDto> getTimelines() {
        try {
            List<Timeline> Timelines = TimelineRepository.findAll();
            List<TimelineResponseDto> TimelineResponseDtos = null;
            if (!Timelines.isEmpty()) {
                TimelineResponseDtos = new ArrayList<>();
                for (Timeline Timeline : Timelines) {
                    TimelineResponseDtos.add(modelMapper.map(Timeline, TimelineResponseDto.class));
                }
            }
            return TimelineResponseDtos;
        } catch (Exception ex) {
            LOGGER.error("Error occurred while processing getTimelines", ex);
            throw new RuntimeException("Error occurred while processing getTimelines", ex);
        }
    }

    @Override
    public String deleteTimeline(Long TimelineId) {
        if (TimelineRepository.findById(TimelineId).isPresent()) {
            TimelineRepository.deleteById(TimelineId);
            return "Timeline Deleted Successfully!!";
        }
        return "No Timeline exists in the database with the provided id.";
    }

    @Override
    public TimelineResponseDto updateTimeline(Long TimelineId, TimelineRequestDto TimelineRequestDto) {
        try {
            Optional<Timeline> optionalTimeline = TimelineRepository.findById(TimelineId);
            if (optionalTimeline.isPresent()) {
                Timeline Timeline = optionalTimeline.get();
                // Update Timeline fields based on TimelineRequestDto

                if (TimelineRequestDto.getTimelineContact() != null) {
                    TimelineContact TimelineContact = modelMapper.map(TimelineRequestDto.getTimelineContact(), TimelineContact.class);
                    Timeline.setTimelineContact(TimelineContact);
                }

                // update address
                if (TimelineRequestDto.getAddress() != null) {
                    List<Address> addressList = List.of(modelMapper.map(TimelineRequestDto.getAddress(), Address[].class));
                    Timeline.setAddress(addressList);
                }

                // update Timeline data
                if (TimelineRequestDto.getTimelineData() != null) {
                    List<TimelineData> TimelineDataList = List.of(modelMapper.map(TimelineRequestDto.getTimelineData(), TimelineData[].class));
                    Timeline.setTimelineData(TimelineDataList);
                }

                // Update other fields
                // ...

                Timeline.setLast_updated_by("admin");
                Timeline.setLast_updated_on(System.currentTimeMillis());

                return modelMapper.map(TimelineRepository.save(Timeline), TimelineResponseDto.class);
            } else {
                return null;
            }
        } catch (Exception ex) {
            LOGGER.error("Error occurred while processing updateTimelines", ex);
            throw new RuntimeException("Error occurred while processing updateTimelines", ex);
        }
    }

    @Override
    public Long addTimeline(Timeline Timeline) {
        try {
            Timeline newTimeline = TimelineRepository.save(Timeline);
            return newTimeline.getId();
        } catch (Exception ex) {
            LOGGER.error("Error occurred while processing addTimelines", ex);
            throw new RuntimeException("Error occurred while processing addTimelines", ex);
        }
    }
}
