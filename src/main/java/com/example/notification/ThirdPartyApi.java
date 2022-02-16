package com.example.notification;

import com.example.notification.data.models.response.ResponseByIdDTO;
import com.example.notification.data.models.thirdPartyApi.*;
import com.example.notification.service.ElasticSearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
@Component
public class ThirdPartyApi {
    private final ElasticSearchService elasticSearchService;

    public ThirdPartyApi(ElasticSearchService elasticSearchService) {
        this.elasticSearchService = elasticSearchService;
    }

    public  ResponseEntity<ThirdPartyResponse> sendUsingApi(ResponseByIdDTO responseByIdDTO) throws JsonProcessingException {
        System.out.println("called - " + responseByIdDTO.getContent());
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("key" , "93ceffda-5941-11ea-9da9-025282c394f2");
//        httpHeaders.add("Api Key" , "93ceffda-5941-11ea-9da9-025282c394f2");
        String uri  = "https://api.imiconnect.in/resources/v1/messaging";

        String delivery_channel = "sms";
        Channels channels = new Channels();
        Sms new_sms = new Sms();
        new_sms.setText(responseByIdDTO.getContent());
        channels.setSms(new_sms);
//        System.out.println(channels.toString());

        Destination destination = new Destination();
        ArrayList<Destination> destinationArrayList = new ArrayList<>();

        ArrayList<String> msisdn = new ArrayList<>();
        msisdn.add(responseByIdDTO.getPhone_number());

        String correlation_id = "123";

        destination.setMsisdn(msisdn);
        destination.setCorrelation_id(correlation_id);
        destinationArrayList.add(destination);

        ThirdPartyRequest new_req= ThirdPartyRequest.builder().delivery_channel(delivery_channel).destination(destinationArrayList).channels(channels).build() ;
        HttpEntity<ThirdPartyRequest> request = new HttpEntity<>(new_req,httpHeaders);

        ResponseEntity<ThirdPartyResponse> thirdPartyResponseResponseEntity = restTemplate.postForEntity(uri , request , ThirdPartyResponse.class);

        System.out.println(thirdPartyResponseResponseEntity.getBody().toString());
//        responseByIdDTO.setStatus(String.valueOf(thirdPartyResponseResponseEntity.getBody().getResponse().get));


        ElasticSearchDocument sent_msg = new ElasticSearchDocument();

        sent_msg.setContent(responseByIdDTO.getContent());
        sent_msg.setId(responseByIdDTO.getId().toString());
        sent_msg.setRequestId(responseByIdDTO.getRequestId());
        sent_msg.setPhoneNumber(responseByIdDTO.getPhone_number());
        sent_msg.setCreatedAt(responseByIdDTO.getCreatedAt());
        sent_msg.setUpdatedAt(responseByIdDTO.getUpdatedAt());

        elasticSearchService.createMessageIndex(sent_msg);

        return thirdPartyResponseResponseEntity;

    }
}
