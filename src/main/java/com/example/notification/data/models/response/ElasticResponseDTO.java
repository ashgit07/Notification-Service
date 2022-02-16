package com.example.notification.data.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class ElasticResponseDTO {
    @JsonProperty("response")
    private List<ESResponse> responseList;
    @Data
    public static class ESResponse{
        @JsonProperty("id")
        private String id;
                @JsonProperty("content")
        private String content;
                @JsonProperty("phoneNumber")
        private String phoneNumber;
                @JsonProperty("createdAt")
        private String createdAt;
                @JsonProperty("updatedAt")
        private String updatedAt;

    }
}
