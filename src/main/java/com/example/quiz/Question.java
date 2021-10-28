package com.example.quiz;

import lombok.Getter;
import lombok.Setter;

public class Question {
    @Getter
    @Setter
    private int questionId;
    private boolean trueAnswer;

    public Question(int questionId, boolean trueAnswer) {
        this.questionId = questionId;
        this.trueAnswer = trueAnswer;
    }

    public boolean isTrueAnswer() {
        return trueAnswer;
    }
}
