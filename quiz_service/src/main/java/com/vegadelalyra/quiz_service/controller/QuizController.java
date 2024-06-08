package com.vegadelalyra.quiz_service.controller;

import com.vegadelalyra.quiz_service.dao.model.QuestionWrapper;
import com.vegadelalyra.quiz_service.dao.model.Response;
import com.vegadelalyra.quiz_service.service.QuizService;
import com.vegadelalyra.quiz_service.service.dto.QuizDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping()
    public ResponseEntity<String> createQuiz(@RequestBody QuizDTO quizDTO) {
        return quizService.createQuiz(quizDTO.getCategoryName(), quizDTO.getNumQuestions(), quizDTO.getTitle());
    }

    @GetMapping("{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id) {
            return quizService.getQuizQuestions(id);
    }

    @PostMapping("{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses) {
        return quizService.getScore(id, responses);
    }
}
