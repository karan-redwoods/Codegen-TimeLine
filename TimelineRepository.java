package com.redwoods.Timeline.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redwoods.Timeline.models.Timeline;

public interface TimelineRepository extends JpaRepository<Timeline, Long> {
}
