package com.vegadelalyra.quiz_service.feign;

import com.vegadelalyra.quiz_service.dao.model.QuestionWrapper;
import com.vegadelalyra.quiz_service.dao.model.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
public class QuizInterfaceTest {

    @Autowired
    private QuizInterface quizInterface;

    @MockBean
    private QuizInterface mockQuizInterface;

    @Test
    public void testGetQuestionsForQuiz() {
        List<Integer> mockQuestionIds = Arrays.asList(1, 2, 3, 4, 5);
        when(mockQuizInterface.getQuestionsForQuiz(anyString(), anyInt()))
                .thenReturn(new ResponseEntity<>(mockQuestionIds, HttpStatus.OK));

        ResponseEntity<List<Integer>> response = quizInterface.getQuestionsForQuiz("category1", 5);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockQuestionIds, response.getBody());
    }

    @Test
    public void testGetQuestionsFromId() {
        List<Integer> mockQuestionIds = Arrays.asList(1, 2, 3);
        List<QuestionWrapper> mockQuestionWrappers = Arrays.asList(
                new QuestionWrapper(1, "Title1", "Option1", "Option2", "Option3", "Option4"),
                new QuestionWrapper(2, "Title2", "Option1", "Option2", "Option3", "Option4"),
                new QuestionWrapper(3, "Title3", "Option1", "Option2", "Option3", "Option4")
        );
        when(mockQuizInterface.getQuestionsFromId(anyList()))
                .thenReturn(new ResponseEntity<>(mockQuestionWrappers, HttpStatus.OK));

        ResponseEntity<List<QuestionWrapper>> response = quizInterface.getQuestionsFromId(mockQuestionIds);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockQuestionWrappers, response.getBody());
    }

    @Test
    public void testGetScore() {
        List<Response> mockResponses = Arrays.asList(
                new Response(1, "Answer1"),
                new Response(2, "Answer2"),
                new Response(3, "Answer3")
        );
        when(mockQuizInterface.getScore(anyList()))
                .thenReturn(new ResponseEntity<>(3, HttpStatus.OK));

        ResponseEntity<Integer> response = quizInterface.getScore(mockResponses);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().intValue());
    }
}
