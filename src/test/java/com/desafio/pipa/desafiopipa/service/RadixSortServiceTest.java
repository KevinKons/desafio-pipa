package com.desafio.pipa.desafiopipa.service;

import com.desafio.pipa.desafiopipa.model.Score;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RadixSortServiceTest {

    @Autowired
    private RadixSortService radixSortService;

    @Test
    @DisplayName("radixSort - When receiving non empty array should sort")
    public void test02() {
        Score[] scores = new Score[8];
        scores[0] = new Score(10, 1L);
        scores[1] = new Score(15, 2L);
        scores[2] = new Score(102, 3L);
        scores[3] = new Score(5, 4L);
        scores[4] = new Score(1006, 5L);
        scores[5] = new Score(206, 6L);
        scores[6] = new Score(15, 7L);
        scores[7] = new Score(327, 8L);

        radixSortService.radixSort(scores, 1006);
        for(int i = 1; i < scores.length; i++) {
            assertTrue(scores[i-1].getScore() >= scores[i].getScore());
        }
    }

}