package com.exam.eventmetrics.engine;

import com.exam.eventmetrics.pojoentites.Commit;
import com.exam.eventmetrics.pojoentites.Payload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventProcessor {

    @Autowired
    private SlidingWindowForRepeatedWord slidingWindowForRepeatedWord;

    public void processMessage(Payload payload) {
        List<Commit> commits = payload.getCommits();
        if(commits != null) {
            commits.forEach(commit -> {
                slidingWindowForRepeatedWord.addMessage(commit.getMessage());
                printMessage();
            });
        }else {
            printMessage();
        }
    }

    private void printMessage() {
        System.out.println("Most Repeated in the current frame:");
        slidingWindowForRepeatedWord.printMostUsedWords();
        System.out.println("-----------------------------------");
    }
}
