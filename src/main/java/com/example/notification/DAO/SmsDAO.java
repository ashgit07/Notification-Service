package com.example.notification.DAO;

import com.example.notification.SmsRequests;
import com.example.notification.data.models.request.PhoneNumberWithTimeDTO;
import com.example.notification.data.models.response.ResponseByIdDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface SmsDAO extends JpaRepository<SmsRequests, Integer> {

//    SmsRequests findByRequestId(SmsRequests byRequestId);
    @Query(
            value = "select * from sms_requests " +
                    "where request_id =:requestId ",
            nativeQuery = true
    )
    SmsRequests findByRequestId(
            @Param("requestId") String requestId
    );

    @Query(
            value = "select * from sms_requests " +
                    "where phone_number =:phoneNumber "+
                    "AND created_at >=:start_time "+
                    "AND created_at <=:end_time ",
            nativeQuery = true
    )
    List<SmsRequests> findAllByPhoneNumberByTime(
            @Param("phoneNumber") String phoneNumber,
            @Param("start_time") LocalDateTime start_time,
            @Param("end_time") LocalDateTime end_time

            );
//
//    @Modifying
//    @Transactional
    @Query(
            value = "update sms_requests s set status =:status " +
                    "where s.request_id =:requestId",
            nativeQuery = true
    )
    void updateByRequestId(
            @Param("requestId") String requestId,
            @Param("status") String status
    );
}
