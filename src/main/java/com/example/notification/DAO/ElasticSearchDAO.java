//package com.example.notification.DAO;
//
//import com.example.notification.ElasticSearchDocument;
//import org.springframework.data.elasticsearch.annotations.Query;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//
//import java.util.List;
//
//public interface ElasticSearchDAO extends ElasticsearchRepository<ElasticSearchDocument,Integer> {
//    @Query("{\"match\": {\"phoneNumber\":  \"?0\"}}")
//    List<ElasticSearchDocument> findAllByPhoneNumber(String phoneNumber);
//}
