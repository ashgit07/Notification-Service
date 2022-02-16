package com.example.notification.data.models.thirdPartyApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Response {
    @JsonProperty("code")
    private String code;
    @JsonProperty("transid")
    private String transid;
    @JsonProperty("description")
    private String description;
    @JsonProperty("correlation_id")
    private String correlation_id;

}
