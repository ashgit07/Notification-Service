//package com.example.notification.DAO;
//
//import com.example.notification.BlacklistedUsers;
//import com.example.notification.data.models.response.PhoneNumberResponseDTO;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//public interface BlacklistDAO extends JpaRepository<BlacklistedUsers , Integer> {
//    @Modifying
//    @Transactional
//    @Query(
//            value = "DELETE  from blacklisted_users " +
//                    "where phone_number =:phone_number ",
//            nativeQuery = true
//    )
//    void deleteByPhoneNumber(
//            @Param("phone_number") String phone_number
//    );
//
//    @Query(
//            value = "SELECT phone_number from blacklisted_users " ,
//            nativeQuery = true
//    )
//    List<String> findPhoneNumbers(
//
////            @param("phone_number") String phone_number
//    );
//}
