package com.example.notification.Repository.impl;

import com.example.notification.BlacklistedUsers;
import com.example.notification.Repository.BlackListRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Component
@Repository
public class BlackListRepositoryImplementation implements BlackListRepository{

    private final RedisTemplate<String , String> redisTemplate;
    public BlackListRepositoryImplementation( RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void deleteByPhoneNumber(String phone_number_temp) {
        redisTemplate.opsForSet().remove("USER2" , phone_number_temp);
    }

    @Override
    public void save(BlacklistedUsers user) {
        redisTemplate.opsForSet().add("USER2" , user.getPhone_number());
//        redisTemplate.opsForHash().put("USER" ,"2" ,"34343");

    }

    @Override
    public Set<String> findPhoneNumbers() {
        return  redisTemplate.opsForSet().members("USER2");
    }

    @Override
    public boolean existsByPhoneNumber(String phone_number) {
        return redisTemplate.opsForSet().isMember("USER2" , phone_number);
    }
}















//
//@Service
//public class BlackListRepositoryImplementation implements BlackListRepository {
//    @Autowired
//    BlacklistDAO blacklistDAO;
//
//    @Override
//    public void deleteByPhoneNumber(String phone_number_temp) {
//            blacklistDAO.deleteByPhoneNumber(phone_number_temp);
//    }
//
//    @Override
//    public void save(BlacklistedUsers user) {
//        blacklistDAO.save(user);
//    }
//
//    @Override
//    public List<BlacklistedUsers> findAll() {
//        List<BlacklistedUsers> blacklistedUsers = blacklistDAO.findAll();
//        return blacklistedUsers;
//    }
//
//    @Override
//    public List<String> findPhoneNumbers() {
//        List<String> blacklistedPhoneNumbers = blacklistDAO.findPhoneNumbers();
//        System.out.println("lll");
//        return blacklistedPhoneNumbers;
//    }
//}
