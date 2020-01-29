package com.flightinportugal.api;

import com.flightinportugal.model.RequestEntity;
import com.flightinportugal.service.RequestEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EntityHistoricalController {
    @Autowired
    private RequestEntityService service;

    @GetMapping("/all-requests")
    public List<RequestEntity> getAllRequests() {
        return service.getAllRequests();
    }

    @PostMapping("/erase")
    @ResponseStatus(HttpStatus.OK)
    public void removeHistoricalRequests() {
        service.removeRequestHistory();
    }
}
