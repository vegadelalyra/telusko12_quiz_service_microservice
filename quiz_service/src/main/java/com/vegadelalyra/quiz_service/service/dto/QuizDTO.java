package com.vegadelalyra.quiz_service.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDTO {
    String categoryName;
    Integer numQuestions;
    String title;
}
