package com.desafio.pipa.desafiopipa.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class ScoreStorage {

    private ScoreStorage() {}

    private static ScoreStorage instance;

    public static synchronized ScoreStorage getInstance() {
        if(instance == null)
            instance = new ScoreStorage();
        return instance;
    }

    @Getter
    private Map<Long, Score> scores = new HashMap<>();

    @Getter @Setter
    private int maxScore = 0;
}
