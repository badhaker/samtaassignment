package com.assignment.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalServiceResponse {
    private int questionId;
    private String question;

}
