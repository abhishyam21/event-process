package com.exam.eventmetrics.engine;

import com.exam.eventmetrics.pojoentites.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Slf4j
@Service
public class DateTimeProcessor {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
    private static final Map<Integer, Integer> hoursToCommitsCountMap = new HashMap<>();;

    public void processDateTimeOfEvents(Event event) {
            if(event != null){
                if(isNotEmpty(event.getCreatedAt())){
                    try {
                        Date parseDate = simpleDateFormat.parse(event.getCreatedAt());
                        Integer count = hoursToCommitsCountMap.getOrDefault(parseDate.getHours(), null);
                        count = count == null ? 1: count+1;
                        hoursToCommitsCountMap.put(parseDate.getHours(), count);
                        printHappeningHour();
                    } catch (ParseException e) {
                        log.error("Error while parsing the datetime",e);
                        e.printStackTrace();
                    }
                }
            }
    }

    private void printHappeningHour() {
        HappeningHour happeningHour = mostCommonHourOFDay();
        System.out.printf("Most Happening hour is: %s with events: %s", happeningHour.getHour(), happeningHour.getCount());
        System.out.println();
    }

    public HappeningHour mostCommonHourOFDay(){
        int maxCount = 0;
        HappeningHour happeningHour = new HappeningHour();
        for (Map.Entry<Integer, Integer> entry : hoursToCommitsCountMap.entrySet()) {
            if(maxCount < entry.getValue()){
                maxCount = entry.getValue();
                happeningHour = new HappeningHour(entry.getKey(),maxCount);
            }
        }
        return happeningHour;
    }
}
