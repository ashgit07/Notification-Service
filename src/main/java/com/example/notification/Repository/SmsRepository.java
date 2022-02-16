package com.example.notification.Repository;

import com.example.notification.SmsRequests;
import com.example.notification.data.models.request.PhoneNumberWithTimeDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmsRepository   {
    SmsRequests findByRequestId(String requestId);

//    SmsRequests findById(Integer id);

    void save(SmsRequests user1);

    List<SmsRequests> getAllSms(PhoneNumberWithTimeDTO query);

    void updateByRequestId(String requestId, String status);
}
