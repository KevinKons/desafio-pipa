package com.desafio.pipa.desafiopipa.service;

import com.desafio.pipa.desafiopipa.model.Score;
import org.springframework.stereotype.Service;

@Service
public class BinarySearchService {

    public void searchAndSetPosition(Score[] scores, Score scoreBeingSearched) {
        int begin = 0;
        int mid;
        int end = scores.length - 1;

        while(begin <= end) {
            mid = (begin + end) / 2;
            if(scores[mid].getScore() == scoreBeingSearched.getScore()) {
                if(scores[mid].getUserId() == scoreBeingSearched.getUserId())
                    scoreBeingSearched.setPosition(mid + 1);
                else
                    scoreBeingSearched.setPosition(findPosition(scores, scoreBeingSearched, mid));
                break;
            } else if(scores[mid].getScore() > scoreBeingSearched.getScore())
                begin = mid + 1;
            else if(scores[mid].getScore() < scoreBeingSearched.getScore())
                end = mid - 1;
        }
    }

    protected int findPosition(Score[] scores, Score scoreBeingSearched, int index) {
        int currentIndex = 0;
        for(int operation = 0; operation <= 1; operation++) {
            currentIndex = index;
            while(scoreBeingSearched.getUserId() != scores[currentIndex].getUserId() && scoreBeingSearched.getScore() == scores[currentIndex].getScore()) {
                if(operation == 0)
                    currentIndex++;
                else
                    currentIndex--;
            }
            if(scoreBeingSearched.getUserId() == scores[currentIndex].getUserId()) break;
        }
        return currentIndex + 1;
    }
}
