package com.example.notification.controller;

import com.example.notification.ElasticSearchDocument;
import com.example.notification.data.constants.ApiConstants;
import com.example.notification.data.models.request.MessageDTO;
import com.example.notification.data.models.request.PhoneNumberDTO;
import com.example.notification.data.models.request.PhoneNumberWithTimeDTO;
import com.example.notification.data.models.request.RequestDTO;
import com.example.notification.data.models.response.*;
import com.example.notification.exceptions.BadRequestException;
import com.example.notification.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController

@RequestMapping(path = ApiConstants.BASE_URL)
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(path = ApiConstants.HEALTH_CHECK)
    public ResponseEntity<ResponseDTO> health(){
        return ResponseEntity.ok().body(ResponseDTO.builder().status("ok").build());
    }

    @RequestMapping(path = ApiConstants.SEND_SMS , method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponseDTO> sendSMS(@RequestBody MessageDTO message) throws BadRequestException {
        System.out.println(message);
        try {
            MessageResponseDTO messageResponseDTO = notificationService.sendSMS(message.getPhone_number(), message.getContent());
            return ResponseEntity.status(HttpStatus.CREATED).body(messageResponseDTO);
        }
        catch(BadRequestException ex){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @RequestMapping(path = ApiConstants.GET_ALL_BLACKLIST )
    public ResponseEntity<PhoneNumberResponseDTO> getAllBlacklist(){
        return ResponseEntity.ok().body(notificationService.getAllBlacklist());
    }

    @RequestMapping(path = ApiConstants.ADD_TO_BLACKLIST , method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> addToBlacklist(@RequestBody PhoneNumberDTO list) throws JsonProcessingException {
        List<String> list1 = list.getPhone_numbers();
        notificationService.addToBlacklist(list1);
        return ResponseEntity.ok().body(ResponseDTO.builder().status("successfully blacklisted").build());
    }

    @RequestMapping(path = ApiConstants.REMOVE_FROM_BLACKLIST , method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> removeFromBlacklist(@RequestBody PhoneNumberDTO list){
        List<String> list1 = list.getPhone_numbers();
        notificationService.removeFromBlacklist(list1);
        return ResponseEntity.ok().body(ResponseDTO.builder().status("successfully removed from  blacklist").build());
    }

    @RequestMapping(path = ApiConstants.GET_SMS)
    public ResponseEntity<ResponseByIdDTO> getDetailsSms(@RequestBody RequestDTO query) throws Exception {
        try {
            return ResponseEntity.ok().body(notificationService.getDetailsSms(query.getRequestId()));
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage());
//            return ResponseEntity.badRequest().body(ResponseByIdDTO.builder().comment(e.getMessage()).build());
        }
    }

    @RequestMapping(path=ApiConstants.GET_ALL_SMS_OF_PHONE_NUMBER_WITH_TIME)
    public ResponseEntity<SearchPage<ElasticSearchDocument>> getSmsListWithPhoneWithTime(@RequestBody PhoneNumberWithTimeDTO query) throws BadRequestException {
        try {
            SearchPage<ElasticSearchDocument> entries = notificationService.getSmsListWithPhoneWithTime(query);
            return ResponseEntity.ok().body(entries);
        }
        catch (BadRequestException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage());
        }
    }

    @RequestMapping(path=ApiConstants.GET_ALL_SMS_WITH_TEXT)
    public ResponseEntity<SearchPage<ElasticSearchDocument>> getSmsListWithText(@RequestBody RequestDTO query){
        SearchPage<ElasticSearchDocument> entries =  notificationService.getSmsListWithText(query);
        return ResponseEntity.ok().body(entries);

    }
}
