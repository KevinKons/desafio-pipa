package com.desafio.pipa.desafiopipa.controller;

import com.desafio.pipa.desafiopipa.model.Highscores;
import com.desafio.pipa.desafiopipa.model.Score;
import com.desafio.pipa.desafiopipa.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/score")
public class ScoreController {

    @Autowired
    ScoreService scoreService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> saveScore(@RequestBody Score score) {
        scoreService.saveScore(score);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}/position", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getUserPosition(@PathVariable long userId) {
        Optional<Score> optScore = scoreService.getScore(userId);
        if(optScore.isPresent())
            return new ResponseEntity<>(optScore.get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/highscorelist", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getHighScoreList() {
        Highscores scores = scoreService.list();
        return new ResponseEntity<>(scores, HttpStatus.OK);
    }
}