package com.example.notification.data.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class PhoneNumberWithTimeDTO {
        @JsonProperty("phone_number")
        private String phone_number;
        @JsonProperty("start_time")
        private LocalDateTime start_time;
        @JsonProperty("end_time")
        private LocalDateTime end_time;
        @JsonProperty("page_no")
        private int page_no;
        @JsonProperty("page_size")
        private int page_size;


}
