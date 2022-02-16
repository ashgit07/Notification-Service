package com.example.notification.data.models.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ResponseDTO {
    @JsonProperty("status")
    private String status;
}
