package com.example.notification.kafka;
import com.example.notification.Repository.BlackListRepository;
import com.example.notification.Repository.SmsRepository;
import com.example.notification.SmsRequests;
import com.example.notification.ThirdPartyApi;
import com.example.notification.data.models.response.ResponseByIdDTO;
import com.example.notification.data.models.thirdPartyApi.ThirdPartyResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private final SmsRepository smsRepository;
    private final BlackListRepository blackListRepository;
    private final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private final ObjectMapper objectMapper;
    private final ThirdPartyApi thirdPartyApi;
    public KafkaConsumer(SmsRepository smsRepository, BlackListRepository blackListRepository, ObjectMapper objectMapper, ThirdPartyApi thirdPartyApi) {
        this.smsRepository = smsRepository;
        this.blackListRepository = blackListRepository;
        this.objectMapper = objectMapper;
        this.thirdPartyApi = thirdPartyApi;
    }

    @KafkaListener(topics = "user1", groupId = "group_id")
    public void consume(String requestId) throws JsonProcessingException {
        logger.info(String.format("#### -> Consumed message -> %s", requestId));
        //        ResponseByIdDTO user1 = objectMapper.readValue(message , ResponseByIdDTO.class);
        SmsRequests out = smsRepository.findByRequestId(requestId);
        ResponseByIdDTO new_message = ResponseByIdDTO.builder().id(out.getId())
                .phone_number(out.getPhone_number())
                .status(out.getStatus())
                .createdAt(out.getCreatedAt())
                .updatedAt(out.getUpdatedAt())
                .content(out.getContent())
                .requestId(out.getRequestId())
                .build();
        logger.info(String.format("#### -> Consumed message -> %s", out.getCreatedAt()));
        if(blackListRepository.existsByPhoneNumber(out.getPhone_number())){
            System.out.println("USER IS BLACKLISTED");
        }
        else {
//            ResponseEntity<ThirdPartyResponse> a = thirdPartyApi.sendUsingApi(new_message);
            thirdPartyApi.sendUsingApi(new_message);
//            SmsRequests message = smsRepository.findByRequestId(requestId);
//            message.setStatus("SENT");
            smsRepository.updateByRequestId(requestId , "SENT");

        }
    }
}