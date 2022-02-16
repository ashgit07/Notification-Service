package com.example.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "messageindex")
public class ElasticSearchDocument {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Field(type = FieldType.Text, name = "request_id")
    private String requestId;

    @Field(type = FieldType.Text, name = "content")
    private String content;

    @Field(type = FieldType.Text, name = "phone_number")
    private String phoneNumber;

    @Field(type = FieldType.Text, name = "status")
    private String status;

    @Field(type = FieldType.Date, format = {},pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSSSS" , name = "created_at")
    LocalDateTime createdAt;

    @Field(type = FieldType.Date, format = {},pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSSSS" , name = "updated_at")
    private LocalDateTime updatedAt;



}
