package com.example.notification.data.models.response;

import com.example.notification.SmsRequests;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
//@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseByIdDTO {
    @JsonProperty("ID")
    private Integer id;

    @JsonProperty("requestID")
    private String requestId;
    @JsonProperty("content")
    private String content;
    @JsonProperty("phone_number")
    private String phone_number;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("Created_At")
    private LocalDateTime createdAt;

    @JsonProperty("Updated_At")
    private LocalDateTime updatedAt;

    @JsonProperty("Api")
    private String api;

    @JsonProperty("pageNumber")
    private Integer pageNumber;

    @JsonProperty("pageSize")
    private Integer pageSize;

    @JsonProperty("comment")
    private String comment;

    public SmsRequests _toSmsRequests(){
        SmsRequests entity = new SmsRequests();
        entity.setPhone_number(phone_number);
        entity.setRequestId(requestId);
        entity.setId(id);
        entity.setContent(content);
        entity.setCreatedAt(createdAt);
        entity.setUpdatedAt(updatedAt);
        //set entity values here from StudentDTO
        return entity ;
    }

}
