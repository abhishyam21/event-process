package com.exam.eventmetrics.engine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HappeningHour {
    private Integer hour;
    private Integer count;
}
