package com.example.notification.data.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class PhoneNumberDTO {
    @JsonProperty("phone_numbers")
    private List<String> phone_numbers;
}
