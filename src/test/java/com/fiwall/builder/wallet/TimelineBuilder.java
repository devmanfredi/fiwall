package com.fiwall.builder.wallet;

import com.fiwall.model.Timeline;

import java.time.LocalDateTime;
import java.util.UUID;

public class TimelineBuilder {
    private Timeline timeline;

    public static TimelineBuilder timeline() {
        TimelineBuilder builder = new TimelineBuilder();
        builder.timeline = Timeline.builder()
                .id(1L)
                .action("Deposit")
                .dateTransaction(LocalDateTime.now())
                .accountBalance("5000")
                .valueTransaction("1000")
                .walletId(UUID.randomUUID())
                .build();
        return builder;
    }

    public Timeline build() {
        return timeline;
    }
}
