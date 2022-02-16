package com.example.notification.data.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MessageDTO {
    @JsonProperty("phone_number")
    private String phone_number;
    @JsonProperty("message")
    private String content;
}
