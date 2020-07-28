package com.exam.eventmetrics.controller;

import com.exam.eventmetrics.engine.DateTimeProcessor;
import com.exam.eventmetrics.engine.HappeningHour;
import com.exam.eventmetrics.engine.SlidingWindowForRepeatedWord;
import com.exam.eventmetrics.engine.Word;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.TreeSet;

@Slf4j
@RestController
@RequestMapping("/metrics")
public class EventMetricsController {

    @Autowired
    private SlidingWindowForRepeatedWord slidingWindowForRepeatedWord;

    @Autowired
    private DateTimeProcessor dateTimeProcessor;

    @Operation(summary = "Get the most repeated word in the given time frame")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got the frame",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Word.class))})
    })
    @GetMapping("/repeated-word")
    public ResponseEntity<TreeSet<Word>> getMostRepeatedWords(){
        log.info("In repeated-word API");
        TreeSet<Word> words = slidingWindowForRepeatedWord.calculateMostRepeatedWords();
        return ResponseEntity.ok().body(words);
    }

    @Operation(summary = "Get the most common hour of the day")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got the hour",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = HappeningHour.class))})
    })
    @GetMapping("/common-hour")
    public ResponseEntity<HappeningHour> getMostCommonHour(){
        log.info("In common-hour API");
        HappeningHour happeningHour = dateTimeProcessor.mostCommonHourOFDay();
        return ResponseEntity.ok().body(happeningHour);
    }
}
