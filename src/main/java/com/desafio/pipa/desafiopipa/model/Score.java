package com.desafio.pipa.desafiopipa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Score {

    private int score;
    private long userId;
    private int position;

    public Score(int score, long userId) {
        this.score = score;
        this.userId = userId;
    }

    public void sumToScore(int newScore) {
        this.score += newScore;
    }
}