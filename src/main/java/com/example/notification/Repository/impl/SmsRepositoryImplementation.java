package com.example.notification.Repository.impl;

import com.example.notification.DAO.SmsDAO;
import com.example.notification.Repository.SmsRepository;
import com.example.notification.SmsRequests;
import com.example.notification.data.models.request.PhoneNumberWithTimeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmsRepositoryImplementation implements SmsRepository {
    final
    SmsDAO smsDAO;

    public SmsRepositoryImplementation(SmsDAO smsDAO) {
        this.smsDAO = smsDAO;
    }

    @Override
    public SmsRequests findByRequestId(String requestId) {
        return smsDAO.findByRequestId(requestId);
//        return smsDAO.findByRequestId(findByRequestId(requestId));
    }



    @Override
    public void save(SmsRequests user1) {
        smsDAO.save(user1);
    }

    @Override
    public List<SmsRequests> getAllSms(PhoneNumberWithTimeDTO query) {
        System.out.println("time=" + query.getStart_time());System.out.println("phone_number=" + query.getPhone_number());
        return smsDAO.findAllByPhoneNumberByTime(query.getPhone_number(), query.getStart_time() , query.getEnd_time());
    }

    @Override
    public void updateByRequestId(String requestId, String status) {
        smsDAO.updateByRequestId(requestId,status);
    }


}
