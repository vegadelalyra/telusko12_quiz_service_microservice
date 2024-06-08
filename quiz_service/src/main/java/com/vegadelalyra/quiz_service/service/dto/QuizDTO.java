package com.vegadelalyra.quiz_service.service.dto;

import lombok.Data;

@Data
public class QuizDTO {
    String categoryName;
    Integer numQuestions;
    String title;
}
