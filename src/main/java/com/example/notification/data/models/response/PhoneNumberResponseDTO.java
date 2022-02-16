package com.example.notification.data.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class PhoneNumberResponseDTO {
    @JsonProperty("phone_numbers")
    private Set<String> phone_numbers;
}
