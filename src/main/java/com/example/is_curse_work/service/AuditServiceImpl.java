package com.example.is_curse_work.service;

import com.example.is_curse_work.model.AuditLog;
import com.example.is_curse_work.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

@Service
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogs;

    public AuditServiceImpl(AuditLogRepository auditLogs) {
        this.auditLogs = auditLogs;
    }

    @Override
    public void log(String actorEmail, String action, String entityType, String entityId, String details) {
        AuditLog log = new AuditLog();
        log.setActorEmail(actorEmail);
        log.setAction(action);
        log.setEntityType(entityType);
        log.setEntityId(entityId);
        log.setDetails(details);
        auditLogs.save(log);
    }
}
