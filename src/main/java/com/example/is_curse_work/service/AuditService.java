package com.example.is_curse_work.service;

public interface AuditService {
    void log(String actorEmail, String action, String entityType, String entityId, String details);
}
