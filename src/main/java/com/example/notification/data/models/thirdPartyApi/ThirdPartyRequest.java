package com.example.notification.data.models.thirdPartyApi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
//@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ThirdPartyRequest {
    @JsonProperty("deliverychannel")
    private String delivery_channel;

    @JsonProperty("channels")
    private Channels channels;


    @JsonProperty("destination")
    private List<Destination> destination;



}
