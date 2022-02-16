package com.example.notification.data.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestDTO {
    @JsonProperty("text")
    private String text;
    @JsonProperty("page_no")
    private Integer pageNumber;
    @JsonProperty("page_size")
    private Integer pageSize;
    @JsonProperty("requestId")
    private String requestId;
}
