package com.example.notification.service;

import com.example.notification.BlacklistedUsers;
import com.example.notification.Repository.BlackListRepository;
import com.example.notification.Repository.SmsRepository;
import com.example.notification.SmsRequests;
import com.example.notification.ThirdPartyApi;
import com.example.notification.data.constants.GeneralConstants;
import com.example.notification.data.models.request.PhoneNumberWithTimeDTO;
import com.example.notification.data.models.request.RequestDTO;
import com.example.notification.data.models.response.PhoneNumberResponseDTO;
import com.example.notification.data.models.response.ResponseByIdDTO;
import com.example.notification.data.models.thirdPartyApi.Sms;
import com.example.notification.exceptions.BadRequestException;
import com.example.notification.kafka.KafkaProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    private NotificationService notificationService;
    @Mock
     SmsRepository smsRepository;
    @Mock
     KafkaTemplate kafkaTemplate;
    @Mock
     BlackListRepository blackListRepository ;
    @Mock
    KafkaProducer kafkaProducer;

    @Mock
    ThirdPartyApi thirdPartyApi;
    @Mock
    ElasticSearchService elasticSearchService;
    @BeforeEach
    void setUp() {
        this.notificationService = new NotificationService( this.smsRepository ,
                this.kafkaTemplate , this.kafkaProducer,
                this.blackListRepository, this.thirdPartyApi,
                this.elasticSearchService  );
    }

    @Test
    void sendSMS_whenPhoneNumberIsNull() throws Exception {
        notificationService.sendSMS("", "hi");
          verify(smsRepository , Mockito.times(0)).save(any(SmsRequests.class));
          verify(kafkaProducer, Mockito.times(0)).sendMessage(anyString());
    }
    @Test
    void sendSMS_whenEverythingIsValid() throws Exception {
        notificationService.sendSMS("+917665491265" , "hi");
          verify(smsRepository , Mockito.times(1)).save(any(SmsRequests.class));
          verify(kafkaProducer ).sendMessage(any(String.class));
    }

    @Test
    void addToBlacklist() throws JsonProcessingException {
//        Set<String> expected = new HashSet<>();
//        expected.add("+1111");
        List<String> list= new ArrayList<>();
        list.add("+1111");
        notificationService.addToBlacklist(list);
        verify(blackListRepository).save(any(BlacklistedUsers.class));
//        Mockito.when(blackListRepository.findPhoneNumbers()).thenReturn(expected);
        Set<String> phone_numbers =  blackListRepository.findPhoneNumbers();
        System.out.println("size=" + phone_numbers.size());
//
//        assertEquals(expected ,phone_numbers );

    }

    @Test //:(
    void removeFromBlacklist() {
        List<String> list = new ArrayList<>();
        list.add("+1111");
        notificationService.removeFromBlacklist(list);
        verify(blackListRepository).deleteByPhoneNumber(anyString());
    }

    @Test
    void getAllBlacklist() {
        notificationService.getAllBlacklist();
        verify(blackListRepository).findPhoneNumbers();
    }

    @Test
    void test_getDetailsSms_whenRequestIdNotFound_shouldReturnException() throws Exception {
        String requestId = "abc";
//        Mockito.when(notificationService.getDetailsSms(requestId)).thenThrow();
//        ResponseByIdDTO response = notificationService.getDetailsSms(requestId);
//        System.out.println(response);
        boolean isExceptionCatched = false;
        try{
            notificationService.getDetailsSms(requestId);
        }
        catch (Exception e){
            isExceptionCatched = true;
            assertEquals(GeneralConstants.REQUEST_ID_NOT_FOUND, e.getMessage());
        }
        assertEquals(true , isExceptionCatched);

    }
    @Test //MUST DISCUSS WITH MENTOR
    void test_getDetailsSms_whenEveryThingIsValid() throws Exception {
        String requestId  = "abc";
        SmsRequests new_message = new SmsRequests();
        new_message.setRequestId("abc");
        new_message.setContent("hi");
        new_message.setPhone_number("+91");

        Mockito.when(smsRepository.findByRequestId(requestId)).thenReturn(new_message);
        ResponseByIdDTO response = notificationService.getDetailsSms(requestId);
        SmsRequests actual = response._toSmsRequests();
        assertEquals(new_message , actual);
    }


    @Test
    void getSmsListWithPhoneWithTime_phoneIsInvalid_ShouldReturnException() {
        PhoneNumberWithTimeDTO query = new PhoneNumberWithTimeDTO();
        query.setPhone_number("+9189");
        query.setStart_time(LocalDateTime.now());
        query.setEnd_time(LocalDateTime.now());
        query.setPage_no(1);
        query.setPage_size(1);
        boolean isExceptionCatched = false;
        try{
            notificationService.getSmsListWithPhoneWithTime(query);
        }
        catch (Exception e){
            isExceptionCatched = true;
            assertEquals(GeneralConstants.PHONE_IS_INVALID, e.getMessage());
        }
        assertEquals(true , isExceptionCatched);

    }
    @Test
    void getSmsListWithPhoneWithTime_phoneIsNull_ShouldReturnException() {
        PhoneNumberWithTimeDTO query = new PhoneNumberWithTimeDTO();
        query.setStart_time(LocalDateTime.now());
        query.setEnd_time(LocalDateTime.now());
        query.setPage_no(1);
        query.setPage_size(1);
        boolean isExceptionCatched = false;
        try{
            notificationService.getSmsListWithPhoneWithTime(query);
        }
        catch (Exception e){
            isExceptionCatched = true;
            assertEquals(GeneralConstants.PHONE_IS_MANDATORY, e.getMessage());
        }
        assertEquals(true , isExceptionCatched);

    }
    @Test
    void getSmsListWithPhoneWithTime_whenEverythingIsFine() throws BadRequestException {
        PhoneNumberWithTimeDTO query = new PhoneNumberWithTimeDTO();
        query.setPhone_number("+917665491265");
        query.setStart_time(LocalDateTime.now());
        query.setEnd_time(LocalDateTime.now());
        query.setPage_no(1);
        query.setPage_size(1);
        notificationService.getSmsListWithPhoneWithTime(query);
        verify(elasticSearchService).findByPhoneNumberByTime(any(PhoneNumberWithTimeDTO.class));
    }

    @Test
    void getSmsListWithText() {
        RequestDTO query = new RequestDTO();
        query.setText("WITHOUT");
        query.setPageNumber(1);
        query.setPageSize(1);
        notificationService.getSmsListWithText(query);
        verify(elasticSearchService).findByText(any(RequestDTO.class));

    }

}