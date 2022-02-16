package com.example.notification.data.models.thirdPartyApi;

import com.example.notification.data.models.thirdPartyApi.Channels;
import com.example.notification.data.models.thirdPartyApi.Destination;
import com.example.notification.data.models.thirdPartyApi.Sms;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
//@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ThirdPartyResponse {
    @JsonProperty("response")
    private List<Response> response;



}
