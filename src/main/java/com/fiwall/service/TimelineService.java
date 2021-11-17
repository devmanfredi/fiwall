package com.fiwall.service;

import com.fiwall.model.Timeline;
import com.fiwall.repository.TimelineRepository;
import org.springframework.stereotype.Service;

@Service
public class TimelineService {

    public TimelineService(TimelineRepository tmRepository) {
        this.tmRepository = tmRepository;
    }

    private final TimelineRepository tmRepository;

    public void save(Timeline timeline) {
        tmRepository.save(timeline);
    }
}
