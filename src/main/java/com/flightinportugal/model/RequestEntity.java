package com.flightinportugal.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@Data
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private LocalDateTime createdAt;

    @PrePersist
    void createdAd() {
        this.createdAt = LocalDateTime.now();
    }
}
