package com.assignment.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NextQuestionResponse {

    private String question_id;

    private String question;
}
