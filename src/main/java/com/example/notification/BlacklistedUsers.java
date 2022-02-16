package com.example.notification;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.io.Serializable;

@Data
//@Entity
public class BlacklistedUsers implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private String id;

    @Column(name = "phone_number")
    private String phone_number;

    public BlacklistedUsers(String id , String phone_number) {
        this.id = id;
        this.phone_number = phone_number;
    }

    public BlacklistedUsers() {

    }
}
