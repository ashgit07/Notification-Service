package com.example.notification.data.models.thirdPartyApi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Destination {
    public List<String> msisdn;
    public String correlation_id;
}
