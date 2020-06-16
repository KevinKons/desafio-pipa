package com.desafio.pipa.desafiopipa.service;

import com.desafio.pipa.desafiopipa.model.Score;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BinarySearchServiceTest {

    @Autowired
    private BinarySearchService binarySearchService;

    @Test
    @DisplayName("findPosition - When score being searched is after index")
    public void test01() {
        Score scoreBeingSearched = new Score(20, 3L);
        Score[] scores = new Score[]{
                new Score(31, 1L),
                new Score(20, 2L),
                new Score(20, 4L),
                scoreBeingSearched,
                new Score(20, 5L),
                new Score(25, 6L),
                new Score(15, 7L),
        };

        int position = binarySearchService.findPosition(scores, scoreBeingSearched, 2);

        assertEquals(4, position);
    }

    @Test
    @DisplayName("findPosition - When score being searched is before index")
    public void test02() {
        Score scoreBeingSearched = new Score(20, 3L);
        Score[] scores = new Score[]{
                new Score(31, 1L),
                new Score(20, 2L),
                scoreBeingSearched,
                new Score(20, 4L),
                new Score(20, 5L),
                new Score(25, 6L),
                new Score(15, 7L),
        };

        int position = binarySearchService.findPosition(scores, scoreBeingSearched, 3);

        assertEquals(3, position);
    }

    @Test
    @DisplayName("searchAndSetPosition - Search the position of the score being passed through parms and set in " +
            "the score its position when there is only one score in the array")
    public void test03() {
        Score scoreBeingSearched = new Score(20, 3L);
        Score[] scores = new Score[]{
                scoreBeingSearched,
        };

        binarySearchService.searchAndSetPosition(scores, scoreBeingSearched);

        assertEquals(1, scoreBeingSearched.getPosition());
    }

    @Test
    @DisplayName("searchAndSetPosition - Search the position of the score being passed through parms and set in " +
            "the score its position when there is only one score in the array")
    public void test04() {
        Score scoreBeingSearched = new Score(30000, 3L);
        Score[] scores = new Score[]{
                scoreBeingSearched,
                new Score(20, 4L),
                new Score(20, 5L),
                new Score(25, 6L),
                new Score(15, 7L),
        };

        binarySearchService.searchAndSetPosition(scores, scoreBeingSearched);

        assertEquals(1, scoreBeingSearched.getPosition());
    }

    @Test
    @DisplayName("searchAndSetPosition - Search the position of the score being passed through parms and set in " +
            "the score its position")
    public void test05() {
        Score scoreBeingSearched = new Score(20, 3L);
        Score[] scores = new Score[]{
                new Score(31, 1L),
                new Score(20, 2L),
                scoreBeingSearched,
                new Score(20, 4L),
                new Score(20, 4L),
                new Score(20, 5L),
                new Score(25, 6L),
                new Score(15, 7L),
        };

        binarySearchService.searchAndSetPosition(scores, scoreBeingSearched);

        assertEquals(3, scoreBeingSearched.getPosition());
    }

}