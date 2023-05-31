package com.assignment.service;

import com.assignment.request.QuestionRequest;
import com.assignment.response.ExternalServiceResponse;
import com.assignment.response.QuestionResponse;

import java.util.List;

public interface ExternalService {
    String fetchQuestions();

    List<ExternalServiceResponse> getAllQuestion();

    QuestionResponse createAnswer(QuestionRequest questionRequest);

}