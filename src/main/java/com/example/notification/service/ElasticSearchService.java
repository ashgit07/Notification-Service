package com.example.notification.service;

import com.example.notification.ElasticSearchDocument;
import com.example.notification.data.models.request.PhoneNumberWithTimeDTO;
import com.example.notification.data.models.request.RequestDTO;
import com.example.notification.data.models.response.ElasticResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ElasticSearchService  {

    private static final String MESSAGE_INDEX = "messageindex";
    private final ElasticsearchOperations elasticsearchOperations;
@Autowired
    public ElasticSearchService( ElasticsearchOperations elasticsearchOperations){

        this.elasticsearchOperations = elasticsearchOperations;
    }
    public void createMessageIndex(ElasticSearchDocument messageDocument) {

        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(messageDocument.getId().toString())
                .withObject(messageDocument).build();

        String documentId = elasticsearchOperations
                .index(indexQuery, IndexCoordinates.of(MESSAGE_INDEX));

//        return documentId;
    }
    public ElasticResponseDTO findByPhoneNumber(String phoneNumber, int pageNumber , int pageSize) {
        MatchQueryBuilder queryBuilder =
                 QueryBuilders
                        .matchQuery("phone_number" ,phoneNumber);

        NativeSearchQuery searchQuery =  new NativeSearchQueryBuilder()
                .withQuery( queryBuilder)
                .build();
        searchQuery.setPageable(PageRequest.of(pageNumber,pageSize));
        SearchHits<ElasticSearchDocument> elasticSearchDocumentSearchHits =
                elasticsearchOperations
                        .search(searchQuery,
                                ElasticSearchDocument.class,
                                IndexCoordinates.of(MESSAGE_INDEX));

        SearchPage<ElasticSearchDocument> response = SearchHitSupport.searchPageFor(elasticSearchDocumentSearchHits , searchQuery.getPageable());

        ElasticResponseDTO required = new ElasticResponseDTO();
//        required.setResponseList(response);
    return required;
    }

    public SearchPage<ElasticSearchDocument> findByPhoneNumberByTime(PhoneNumberWithTimeDTO query) {
        Criteria criteria = new Criteria("phone_number").matches(query.getPhone_number()).and("created_at")
                .greaterThan(query.getStart_time())
                .lessThan(query.getEnd_time());

        Query searchQuery = new CriteriaQuery(criteria);

        searchQuery.setPageable(PageRequest.of(query.getPage_no(), query.getPage_size()));
        SearchHits<ElasticSearchDocument> elasticSearchDocumentSearchHits = elasticsearchOperations
                .search(searchQuery,
                        ElasticSearchDocument.class,
                        IndexCoordinates.of(MESSAGE_INDEX));
        return SearchHitSupport.searchPageFor(elasticSearchDocumentSearchHits , searchQuery.getPageable());
    }
    public SearchPage<ElasticSearchDocument> findByText(RequestDTO query ) {
//        Criteria criteria = new Criteria("content").contains(query.getText());
        Criteria criteria = new Criteria("content").matches(query.getText());
        Query searchQuery = new CriteriaQuery(criteria);
        System.out.println("page size = "  + query.getPageSize());
        searchQuery.setPageable(PageRequest.of(query.getPageNumber(), query.getPageSize()));
        SearchHits<ElasticSearchDocument> elasticSearchDocumentSearchHits = elasticsearchOperations
                .search(searchQuery,
                        ElasticSearchDocument.class,
                        IndexCoordinates.of(MESSAGE_INDEX));
        return SearchHitSupport.searchPageFor(elasticSearchDocumentSearchHits , searchQuery.getPageable());
    }
}
