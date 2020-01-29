package com.flightinportugal.service;

import com.flightinportugal.model.RequestEntity;
import com.flightinportugal.model.RequestEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestEntityService {
    private RequestEntityRepository requestEntityRepository;
    private static final Logger logger = LoggerFactory.getLogger(RequestEntityService.class);

    @Autowired
    public RequestEntityService(RequestEntityRepository requestEntityRepository) {
        this.requestEntityRepository = requestEntityRepository;
    }

    public void saveNewRequest(String url) {
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setUrl(url);

        requestEntityRepository.save(requestEntity);
        logger.info("{} has been saved.",requestEntity);
    }

    public List<RequestEntity> getAllRequests() {
        logger.info("Finding all requests...");
        return requestEntityRepository.findAll();
    }

    public void removeRequestHistory() {
        logger.info("Removing all historical data.");
        requestEntityRepository.deleteAll();
        logger.info("Historical data has been deleted.");
    }
}
