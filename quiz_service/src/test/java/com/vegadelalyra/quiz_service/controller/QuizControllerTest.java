package com.vegadelalyra.quiz_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vegadelalyra.quiz_service.dao.model.QuestionWrapper;
import com.vegadelalyra.quiz_service.dao.model.Response;
import com.vegadelalyra.quiz_service.service.QuizService;
import com.vegadelalyra.quiz_service.service.dto.QuizDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuizController.class)
public class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizService quizService;

    @Autowired
    private ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<List<Response>> responseCaptor;

    @Captor
    private ArgumentCaptor<QuizDTO> quizDTOCaptor;

    private QuizDTO quizDTO;
    private List<QuestionWrapper> mockQuestionWrappers;
    private List<Response> mockResponses;

    @BeforeEach
    public void setUp() {
        quizDTO = new QuizDTO("category1", 3, "Sample Quiz");
        mockQuestionWrappers = Arrays.asList(
                new QuestionWrapper(1, "Title1", "Option1", "Option2", "Option3", "Option4"),
                new QuestionWrapper(2, "Title2", "Option1", "Option2", "Option3", "Option4"),
                new QuestionWrapper(3, "Title3", "Option1", "Option2", "Option3", "Option4")
        );
        mockResponses = Arrays.asList(
                new Response(1, "Answer1"),
                new Response(2, "Answer2"),
                new Response(3, "Answer3")
        );
    }

    @Test
    public void testCreateQuiz() throws Exception {
        when(quizService.createQuiz(anyString(), anyInt(), anyString()))
                .thenReturn(ResponseEntity.status(201).body("success"));

        mockMvc.perform(post("/quiz")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quizDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("success"));
    }

    @Test
    public void testGetQuizQuestions() throws Exception {
        when(quizService.getQuizQuestions(anyInt()))
                .thenReturn(ResponseEntity.ok(mockQuestionWrappers));

        mockMvc.perform(get("/quiz/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[2].id").value(3));
    }

    @Test
    public void testSubmitQuiz() throws Exception {
        when(quizService.getScore(anyInt(), anyList()))
                .thenReturn(ResponseEntity.ok(3));

        mockMvc.perform(post("/quiz/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockResponses)))
                .andExpect(status().isOk())
                .andExpect(content().string("3"));
    }
}
