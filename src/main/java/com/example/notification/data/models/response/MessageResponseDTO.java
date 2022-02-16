package com.example.notification.data.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponseDTO {
    @JsonProperty("requestID")
    private String requestID;
    @JsonProperty("comment")
    private String comment;
}
