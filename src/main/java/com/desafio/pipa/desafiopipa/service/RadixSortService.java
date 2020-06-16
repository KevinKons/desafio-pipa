package com.desafio.pipa.desafiopipa.service;

import com.desafio.pipa.desafiopipa.model.Score;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RadixSortService {

    private final int radix = 10;

    public void radixSort(Score[] scores, int maxScore) {
        for (int exp = 1; maxScore/exp > 0; exp *= radix)
            countingSort(scores, exp);
    }

    private void countingSort(Score[] scores, int exp) {
        List<Score> bucketsArray[] = new ArrayList[radix];
        for (int count = 0; count < bucketsArray.length; count++) {
            bucketsArray[count] = new ArrayList<>();
        }

        for (Score score : scores) {
            bucketsArray[(score.getScore()/exp)%10].add(score);
        }

        int index = 0;
        for(int b = radix - 1; b >= 0; b--) {
            for (Score score : bucketsArray[b]) {
                scores[index] = score;
                index++;
            }
            bucketsArray[b].clear();
        }
    }
}
