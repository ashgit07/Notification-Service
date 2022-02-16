package com.example.notification;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
@Entity
@Table(name= "sms_requests")
public class SmsRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "requestId")
    private String requestId;

    @Column(name = "phone_number" )
    @NonNull
    @NotBlank
//    @Pattern("^([0]|\\\\+91)?\\\\d{10}")
    private String phone_number;

    @Column(name= "content")
    private String content;

    @Column(name="Status")
    private String status;
    //failure_code
    //failure_comments


    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public SmsRequests(int id, String phone_number, String content, String status) {
        this.id = id;
        this.phone_number = phone_number;
        this.content = content;
        this.status = status;
    }

    public SmsRequests() {
    }
}
