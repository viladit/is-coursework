package com.example.is_curse_work.service;

public interface NotificationService {
    int runExpiryBatch(Long fridgeId, int daysBefore);
}

