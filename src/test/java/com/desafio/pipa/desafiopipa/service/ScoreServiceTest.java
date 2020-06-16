package com.desafio.pipa.desafiopipa.service;

import com.desafio.pipa.desafiopipa.model.Score;
import com.desafio.pipa.desafiopipa.model.ScoreStorage;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScoreServiceTest {

    @Autowired
    private ScoreService scoreService;

    private final long userId = 1L;
    private final int userScore = 30000;
    private Score score;

    @Before
    public void before() {
        score = new Score(userScore, userId);
    }

    @Test
    @DisplayName("post - When score doesn't exist should save score")
    public void test00(){
        scoreService.saveScore(score);

        assertEquals(userScore, ScoreStorage.getInstance().getScores().get(userId).getScore());
        assertEquals(userScore, ScoreStorage.getInstance().getMaxScore());
        ScoreStorage.getInstance().getScores().clear();
    }

    @Test
    @DisplayName("post - When score already exist should sum to current score")
    public void test01(){
        scoreService.saveScore(score);
        scoreService.saveScore(score);

        final int expectedScore = userScore + userScore;
        assertEquals(expectedScore, ScoreStorage.getInstance().getScores().get(userId).getScore());
        assertEquals(expectedScore, ScoreStorage.getInstance().getMaxScore());
        ScoreStorage.getInstance().getScores().clear();
    }

    @Test
    @DisplayName("get - When score doesn't exist should return empty")
    public void test02() {
        Optional opt = scoreService.getScore(userId);
        assertFalse(opt.isPresent());
    }

    @Test
    @DisplayName("get - When score exist should get it's position (With 2 Scores saved)")
    public void test03() {
        scoreService.saveScore(score);
        scoreService.saveScore(new Score(2, 2L));

        Score score = scoreService.getScore(userId).get();

        assertEquals(1, score.getPosition());
        ScoreStorage.getInstance().getScores().clear();
    }

    @Test
    @DisplayName("get - When score exist should get it's position (With 5 Scores saved)")
    public void test04() {
        scoreService.saveScore(score);
        addScores(4);

        Score score = scoreService.getScore(userId).get();

        assertEquals(1, score.getPosition());
        ScoreStorage.getInstance().getScores().clear();
    }

    @Test
    @DisplayName("list - When score storage is empty should return empty list")
    public void test05() {
        Score[] scores = scoreService.list().getHighscores();
        assertEquals(0, scores.length);
    }

    @Test
    @DisplayName("list - When score storage isn't empty should return ordered list")
    public void test06() {
        addScores(5);

        Score[] scores = scoreService.list().getHighscores();

        for(int i = 1; i < scores.length; i++) {
            assertTrue(scores[i - 1].getScore() >= scores[i].getScore());
            assertTrue(scores[i - 1].getPosition() < scores[i].getPosition());
        }
        ScoreStorage.getInstance().getScores().clear();
    }

    @Test
    @DisplayName("list - When score storage isn't empty and has more MAX_LIST_SIZE itens should return ordered list" +
            "with only MAX_LIST_SIZE itens")
    public void test07() {
        addScores(50000);

        Score[] scores = scoreService.list().getHighscores();

        assertEquals(ScoreService.LIST_MAX_SIZE, scores.length);
        for(int i = 1; i < scores.length; i++) {
            assertTrue(scores[i - 1].getScore() >= scores[i].getScore());
            assertTrue(scores[i - 1].getPosition() < scores[i].getPosition());
        }
        ScoreStorage.getInstance().getScores().clear();
    }

    private void addScores(long amoutOfScores) {
        for(long i = 2; i <= amoutOfScores+1; i++) {
            Random random = new Random(10000);
            Score score = new Score(random.nextInt(10000), i);
            ScoreStorage.getInstance().getScores().put(i, score);
        }
    }
}