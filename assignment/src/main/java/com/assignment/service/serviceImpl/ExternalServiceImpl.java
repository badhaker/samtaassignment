package com.assignment.service.serviceImpl;

import com.assignment.entity.Question;
import com.assignment.exception.QuestionNotFoundException;
import com.assignment.repository.QuestionRepository;
import com.assignment.request.QuestionRequest;
import com.assignment.response.ExternalServiceResponse;
import com.assignment.response.NextQuestionResponse;
import com.assignment.response.QuestionResponse;
import com.assignment.service.ExternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExternalServiceImpl implements ExternalService {

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    RestTemplate restTemplate;

    @Override
    public String fetchQuestions() {
        String apiUrl = "https://jservice.io/api/random";

        for (int i = 1; i <= 5; i++) {
            ResponseEntity<ExternalServiceResponse[]> responseEntity = restTemplate.getForEntity(apiUrl, ExternalServiceResponse[].class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                ExternalServiceResponse[] responses = responseEntity.getBody();

                if (responses != null && responses.length > 0) {
                    ExternalServiceResponse response = responses[0];
                    Question question = new Question();
                    question.setQuestionId(i);
                    question.setQuestion(response.getQuestion());
                    questionRepository.save(question);
                } else {
                    throw new RuntimeException("No questions received from the API");
                }
            } else {
                throw new RuntimeException("Failed to fetch questions from the API");
            }
        }

        return "Data saved in the database";
    }

    public List<ExternalServiceResponse> getAllQuestion(){
        List<Question> questions = questionRepository.findAll();
        return questions.stream().map(this :: convertToQuestion).collect(Collectors.toList());
    }

    private ExternalServiceResponse convertToQuestion(Question question)
    {
        return ExternalServiceResponse.builder()
                .questionId(question.getQuestionId())
                .question(question.getQuestion())
                .build();
    }

    @Override
    public QuestionResponse createAnswer(QuestionRequest questionRequest) {
        int questionId = questionRequest.getQuestion_id();
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found with ID: " + questionId));

        question.setAnswer(questionRequest.getAnswer());
        questionRepository.save(question);

        Question nextQuestion = questionRepository.findById(questionId + 1)
                .orElse(null); // Assumes null is an acceptable value when the next question is not found

        return createQuestionResponse(questionRequest.getAnswer(), nextQuestion);
    }

    private QuestionResponse createQuestionResponse(String answer, Question nextQuestion) {
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setCorrect_answer(answer);

        if (nextQuestion != null) {
            NextQuestionResponse nextQuestionResponse = new NextQuestionResponse();
            nextQuestionResponse.setQuestion_id(String.valueOf(nextQuestion.getQuestionId()));
            nextQuestionResponse.setQuestion(nextQuestion.getQuestion());
            questionResponse.setNext_question(nextQuestionResponse);
        }

        return questionResponse;
    }


}
