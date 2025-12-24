package com.example.is_curse_work.service;

import com.example.is_curse_work.dto.ReportSummary;
import com.example.is_curse_work.dto.ZoneCountDto;
import com.example.is_curse_work.repository.NotificationRepository;
import com.example.is_curse_work.repository.ProductRepository;
import com.example.is_curse_work.repository.ZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final ProductRepository products;
    private final ZoneRepository zones;
    private final NotificationRepository notifications;

    public ReportServiceImpl(ProductRepository products,
                             ZoneRepository zones,
                             NotificationRepository notifications) {
        this.products = products;
        this.zones = zones;
        this.notifications = notifications;
    }

    @Override
    public ReportSummary buildSummary() {
        long expiredCount = products.countByStatus("EXPIRED");
        long notificationCount = notifications.count();
        List<ZoneCountDto> topZones = zones.findTopZonesByProductCount().stream()
                .map(row -> {
                    Long zoneId = (Long) row[0];
                    String zoneName = (String) row[1];
                    String fridgeName = (String) row[2];
                    long count = (long) row[3];
                    return new ZoneCountDto(zoneId, zoneName, fridgeName, count);
                })
                .limit(5)
                .toList();
        return new ReportSummary(expiredCount, notificationCount, topZones);
    }
}
