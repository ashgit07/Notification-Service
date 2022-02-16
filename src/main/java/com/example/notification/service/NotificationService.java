package com.example.notification.service;

import com.example.notification.BlacklistedUsers;
import com.example.notification.ElasticSearchDocument;
import com.example.notification.Repository.BlackListRepository;
//import com.example.notification.Repository.ElasticSearchRepo;
import com.example.notification.Repository.SmsRepository;
import com.example.notification.SmsRequests;
import com.example.notification.ThirdPartyApi;
import com.example.notification.data.constants.ApiConstants;
import com.example.notification.data.constants.GeneralConstants;
import com.example.notification.data.models.request.PhoneNumberWithTimeDTO;
import com.example.notification.data.models.request.RequestDTO;
import com.example.notification.data.models.response.ElasticResponseDTO;
import com.example.notification.data.models.response.MessageResponseDTO;
import com.example.notification.data.models.response.PhoneNumberResponseDTO;
import com.example.notification.data.models.response.ResponseByIdDTO;
import com.example.notification.exceptions.BadRequestException;
import com.example.notification.exceptions.NotFoundException;
import com.example.notification.kafka.KafkaProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
public class NotificationService {

    private  SmsRepository smsRepository;
    private  KafkaTemplate kafkaTemplate;
    private static final String TOPIC = "users1";
    private  KafkaProducer kafkaProducer;
    private  BlackListRepository blackListRepository;
    private  ThirdPartyApi thirdPartyApi;
    private  ElasticSearchService elasticSearchService;
    @Autowired
    public NotificationService(SmsRepository smsRepository,KafkaTemplate kafkaTemplate, KafkaProducer kafkaProducer, BlackListRepository blackListRepository,   ThirdPartyApi thirdPartyApi, ElasticSearchService elasticSearchService) {
        this.smsRepository = smsRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaProducer = kafkaProducer;
        this.blackListRepository = blackListRepository;
        this.thirdPartyApi = thirdPartyApi;
        this.elasticSearchService = elasticSearchService;
    }

    public MessageResponseDTO sendSMS(String phone_number, String content ) throws BadRequestException {
        UUID uuid = UUID.randomUUID();
        String requestId  = uuid.toString();
        SmsRequests user = new SmsRequests();
        String regex = "^([0]|\\+91)?\\d{10}";
        try {
            if(Objects.isNull(phone_number)||phone_number.equals("")){
                throw new BadRequestException(GeneralConstants.PHONE_IS_MANDATORY);
            }
            if(!phone_number.matches(regex)){
                throw new BadRequestException(GeneralConstants.PHONE_IS_INVALID);
            }
            user.setPhone_number(phone_number);
            user.setContent(content);
            user.setRequestId(requestId);
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            smsRepository.save(user);
            kafkaProducer.sendMessage(requestId);
        }
        catch(BadRequestException e){
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
//            return MessageResponseDTO.builder().comment(GeneralConstants.PHONE_IS_MANDATORY).build();

        }
        return MessageResponseDTO.builder().requestID(requestId).comment("saved successfully").build();

    }


    public void addToBlacklist(List<String> list) throws JsonProcessingException {
        for(int i = 0 ; i<list.size();i++){
            BlacklistedUsers user = new BlacklistedUsers();
            user.setPhone_number(list.get(i));
            blackListRepository.save(user);
        }

    }

    public void removeFromBlacklist(List<String> list1) {

        for(int i = 0 ; i< list1.size();i++){
            String phone_number_temp = list1.get(i);

            blackListRepository.deleteByPhoneNumber(phone_number_temp);
        }
    }

    public PhoneNumberResponseDTO getAllBlacklist() {
        Set<String> output;
        output = blackListRepository.findPhoneNumbers();
        return PhoneNumberResponseDTO.builder().phone_numbers(output).build();
    }

    public  ResponseByIdDTO getDetailsSms(String requestId) throws NotFoundException {
//
        try {
            SmsRequests out = smsRepository.findByRequestId(requestId);
            if(Objects.isNull(out)){
                throw new NotFoundException(GeneralConstants.REQUEST_ID_NOT_FOUND);
            }
            return ResponseByIdDTO.builder().id(out.getId())
                    .phone_number(out.getPhone_number())
                    .requestId(out.getRequestId())
                    .status(out.getStatus())
                    .createdAt(out.getCreatedAt())
                    .updatedAt(out.getUpdatedAt())
                    .content(out.getContent())
                    .build();
        }
        catch (NotFoundException e){
            throw new NotFoundException(GeneralConstants.REQUEST_ID_NOT_FOUND);
        }
    }
    public SearchPage<ElasticSearchDocument> getSmsListWithPhoneWithTime(PhoneNumberWithTimeDTO query) throws BadRequestException {
        String phone_number = query.getPhone_number();
        String regex = "^([0]|\\+91)?\\d{10}";
        try {
            if(Objects.isNull(phone_number)||phone_number.equals("")){
                throw new BadRequestException(GeneralConstants.PHONE_IS_MANDATORY);
            }
            if(!phone_number.matches(regex)){
                throw new BadRequestException(GeneralConstants.PHONE_IS_INVALID);
            }
            SearchPage<ElasticSearchDocument> sms_list  = elasticSearchService.findByPhoneNumberByTime(query);
            return  sms_list;
        }
        catch(BadRequestException e){
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
//            return MessageResponseDTO.builder().comment(GeneralConstants.PHONE_IS_MANDATORY).build();

        }

    }
    public SearchPage<ElasticSearchDocument> getSmsListWithText(RequestDTO query) {
        SearchPage<ElasticSearchDocument> sms_list  = elasticSearchService.findByText(query);
        return  sms_list;
    }


}
