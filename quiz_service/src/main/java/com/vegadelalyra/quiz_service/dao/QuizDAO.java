package com.vegadelalyra.quiz_service.dao;

import com.vegadelalyra.quiz_service.dao.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizDAO extends JpaRepository<Quiz, Integer> {
}
