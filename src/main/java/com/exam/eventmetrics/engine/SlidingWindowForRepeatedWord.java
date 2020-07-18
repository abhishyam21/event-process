package com.exam.eventmetrics.engine;


import com.exam.eventmetrics.exceptions.WrongActionException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Data
@Service
public class SlidingWindowForRepeatedWord {
    private TreeSet<Word> wordCounter;
    private Map<String, Word> cacheForWords;
    private int messageCount;
    private List<String> messages;

    public SlidingWindowForRepeatedWord() {
        this.wordCounter = new TreeSet<>((word1, word2) -> {
            int compare = Integer.compare(word2.getCount(), word1.getCount());
            if(compare == 0)
                return word1.getText().compareTo(word2.getText());
            return compare;
        });
        cacheForWords = new HashMap<>();
        messageCount = 0;
        messages = new ArrayList<>(10);
    }

    public void addMessage(String message) {
        if(messageCount >= 10){
            removeMessage();
        }
        Arrays.stream(message.split(" ")).forEach(this::addWord);
        messageCount+=1;
        messages.add(message);
    }

    public void removeMessage() throws WrongActionException {
        if(messageCount <0){
            log.error("Can't remove message");
            throw new WrongActionException("Can't remove message, Window is Empty");
        }
        String message = messages.remove(0);
        Arrays.stream(message.split(" ")).forEach(this::removeWord);
        messageCount-=1;
    }

    public void addWord(String word){
        Word expectedWord = cacheForWords.getOrDefault(word, null);
        if(expectedWord == null){
            expectedWord = new Word(word, 1);
            wordCounter.add(expectedWord);
        }else {
            expectedWord.setCount(expectedWord.getCount()+1);
        }
        cacheForWords.put(word, expectedWord);
    }

    public void removeWord(String word){
        Word expectedWord = cacheForWords.getOrDefault(word, null);
        if(expectedWord == null){
            log.error("No word exist in the Window");
        }else {
            int count = expectedWord.getCount();
            if(count == 1){
                cacheForWords.remove(word);
                wordCounter.remove(expectedWord);
            }else {
                expectedWord.setCount(expectedWord.getCount()-1);
            }
        }
    }

    public TreeSet<Word> calculateMostRepeatedWords(){
        Word highestRank = wordCounter.stream().max(Comparator.comparing(Word::getCount)).orElse(new Word());
        return wordCounter
                .stream()
                .filter(word -> word.getCount() == highestRank.getCount())
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public void printMostUsedWords(){
        TreeSet<Word> words = calculateMostRepeatedWords();
                words.forEach( word ->
                        System.out.println(word.getText()+"-->"+ word.getCount()));
    }
}
