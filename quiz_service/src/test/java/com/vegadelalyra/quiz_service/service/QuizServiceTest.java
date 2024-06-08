package com.vegadelalyra.quiz_service.service;

import com.vegadelalyra.quiz_service.dao.QuizDAO;
import com.vegadelalyra.quiz_service.dao.model.QuestionWrapper;
import com.vegadelalyra.quiz_service.dao.model.Quiz;
import com.vegadelalyra.quiz_service.dao.model.Response;
import com.vegadelalyra.quiz_service.feign.QuizInterface;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuizServiceTest {

    @Mock
    private QuizDAO quizDAO;

    @Mock
    private QuizInterface quizInterface;

    @InjectMocks
    private QuizService quizService;

    @Captor
    private ArgumentCaptor<Quiz> quizCaptor;

    @Test
    public void testCreateQuiz() {
        List<Integer> mockQuestionIds = Arrays.asList(1, 2, 3);
        when(quizInterface.getQuestionsForQuiz(anyString(), anyInt()))
                .thenReturn(new ResponseEntity<>(mockQuestionIds, HttpStatus.OK));

        ResponseEntity<String> response = quizService.createQuiz("category1", 3, "Sample Quiz");

        verify(quizDAO).save(quizCaptor.capture());
        Quiz savedQuiz = quizCaptor.getValue();

        assertEquals("Sample Quiz", savedQuiz.getTitle());
        assertEquals(mockQuestionIds, savedQuiz.getQuestionsIds());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("success " + savedQuiz.toString(), response.getBody());
    }

    @Test
    public void testGetQuizQuestions() {
        Quiz mockQuiz = new Quiz();
        mockQuiz.setTitle("Sample Quiz");
        mockQuiz.setQuestionsIds(Arrays.asList(1, 2, 3));
        when(quizDAO.findById(anyInt())).thenReturn(Optional.of(mockQuiz));

        List<QuestionWrapper> mockQuestionWrappers = Arrays.asList(
                new QuestionWrapper(1, "Title1", "Option1", "Option2", "Option3", "Option4"),
                new QuestionWrapper(2, "Title2", "Option1", "Option2", "Option3", "Option4"),
                new QuestionWrapper(3, "Title3", "Option1", "Option2", "Option3", "Option4")
        );
        when(quizInterface.getQuestionsFromId(anyList()))
                .thenReturn(new ResponseEntity<>(mockQuestionWrappers, HttpStatus.OK));

        ResponseEntity<List<QuestionWrapper>> response = quizService.getQuizQuestions(1);

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
        when(quizInterface.getScore(anyList()))
                .thenReturn(new ResponseEntity<>(3, HttpStatus.OK));

        ResponseEntity<Integer> response = quizService.getScore(1, mockResponses);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().intValue());
    }
}
