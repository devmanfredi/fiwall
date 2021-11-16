package com.fiwall.service;

import com.fiwall.model.Timeline;
import com.fiwall.repository.TimelineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TimelineService {

    private final TimelineRepository tmRepository;

    public void save(Timeline timeline) {
        tmRepository.save(timeline);
    }
}
