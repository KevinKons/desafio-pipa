package com.desafio.pipa.desafiopipa.service;

import com.desafio.pipa.desafiopipa.model.Highscores;
import com.desafio.pipa.desafiopipa.model.Score;
import com.desafio.pipa.desafiopipa.model.ScoreStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScoreService {

    public static final int LIST_MAX_SIZE = 20000;

    @Autowired
    private RadixSortService sortService;

    public void saveScore(Score score) {
        ScoreStorage scoreStorage = ScoreStorage.getInstance();
        Map<Long, Score> scores = scoreStorage.getScores();
        if(scores.containsKey(score.getUserId())) {
            Score existentScore = scores.get(score.getUserId());
            existentScore.sumToScore(score.getScore());
        } else {
            scores.put(score.getUserId(), score);
        }
        updateMaxScore(score.getScore());
    }

    private synchronized void updateMaxScore(int score) {
        ScoreStorage scoreStorage = ScoreStorage.getInstance();
        if(score > scoreStorage.getMaxScore())
            scoreStorage.setMaxScore(score);
    }

    public Optional<Score> getScore(long userId) {
        ScoreStorage scoreStorage = ScoreStorage.getInstance();
        Map<Long, Score> scores = scoreStorage.getScores();
        if(!scores.containsKey(userId))
            return Optional.empty();

        Score[] sortedScores = orderScores(scores);
        return Optional.of(getScoreWithPosition(sortedScores, userId));
    }

    private Score[] orderScores(Map<Long, Score> scores) {
        Score[] scoresArray = scores.values().toArray(new Score[0]);
        int maxScore =  ScoreStorage.getInstance().getMaxScore();
        sortService.radixSort(scoresArray, maxScore);
        return scoresArray;
    }

    //TO DO: Apply binary search
    private Score getScoreWithPosition(Score[] scoresArray, long userId) {
        for(int i = 0; i < scoresArray.length; i++) {
            Score score = scoresArray[i];
            if(score.getUserId() == userId) {
                score.setPosition(i+1);
                return score;
            }
        }
        return null;
    }

    public Highscores list() {
        ScoreStorage scoreStorage = ScoreStorage.getInstance();
        Map<Long, Score> scores = scoreStorage.getScores();
        if(scores.isEmpty())
            return new Highscores(new Score[0]);

        Score[] sortedScores = orderScores(scores);
        Score[] scoresToReturn = sortedScores.length >= LIST_MAX_SIZE ?
                Arrays.copyOfRange(sortedScores, 0,LIST_MAX_SIZE) : sortedScores;
        setPositionOnEachItem(scoresToReturn);
        return new Highscores(scoresToReturn);
    }

    private void setPositionOnEachItem(Score[] scores) {
        for(int i = 0; i < scores.length; i++) {
            scores[i].setPosition(i+1);
        }
    }

}
