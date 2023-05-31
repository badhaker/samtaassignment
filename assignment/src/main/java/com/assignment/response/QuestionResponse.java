package com.assignment.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResponse {
    private String correct_answer;

    private NextQuestionResponse next_question;
}
