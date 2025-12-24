package com.example.is_curse_work.repository;

import com.example.is_curse_work.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    List<Zone> findByFridge_FridgeIdOrderBySortOrderAsc(Long fridgeId);
}

