//package com.example.notification.Repository.impl;
//
//import com.example.notification.DAO.ElasticSearchDAO;
//import com.example.notification.ElasticSearchDocument;
//import com.example.notification.Repository.ElasticSearchRepo;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//@Repository
//public class ElasticSearchRepoImpl implements ElasticSearchRepo {
//    private final ElasticSearchDAO elasticSearchDAO;
//
//    public ElasticSearchRepoImpl(ElasticSearchDAO elasticSearchDAO) {
//        this.elasticSearchDAO = elasticSearchDAO;
//    }
//
//    @Override
//    public List<ElasticSearchDocument> findByPhoneNumber(String phoneNumber) {
//        return elasticSearchDAO.findAllByPhoneNumber(phoneNumber);
//    }
//
//    @Override
//    public void save(ElasticSearchDocument sent_msg) {
//        elasticSearchDAO.save(sent_msg);
//    }
//}
