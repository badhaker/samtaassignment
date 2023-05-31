package com.assignment.controller;

import com.assignment.request.QuestionRequest;
import com.assignment.response.ExternalServiceResponse;
import com.assignment.response.QuestionResponse;
import com.assignment.service.ExternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class QuestionController {
	@Autowired
	ExternalService externalService;
	@GetMapping("/get")
	public String getData(){
		String message = externalService.fetchQuestions();
		return message;
	}

	@GetMapping("/play")
	public List<ExternalServiceResponse> fetchAllQuestion(){
		return externalService.getAllQuestion();
	}

	@PostMapping("/next")
	public QuestionResponse createAnswer(@RequestBody QuestionRequest questionRequest){
		return externalService.createAnswer(questionRequest);

	}

}
