package com.example.notification.Repository;

import com.example.notification.BlacklistedUsers;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@Component
public interface BlackListRepository  {
    void deleteByPhoneNumber(String phone_number_temp);

    void save(BlacklistedUsers user);

//    List<BlacklistedUsers> findAll();
     Set<String> findPhoneNumbers();


    boolean existsByPhoneNumber(String phone_number);
}
