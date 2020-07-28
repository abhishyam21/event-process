package com.exam.eventmetrics.engine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Word implements Comparable<Word>{

    @NotNull
    private String text;
    @NotNull
    private int count;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        if (count != word.count) return false;
        return Objects.equals(text, word.text);
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + count;
        return result;
    }

    @Override
    public int compareTo(Word o) {
        return this.getText().compareTo(o.text);
    }
}
