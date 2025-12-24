package com.example.is_curse_work.service;

import com.example.is_curse_work.dto.ZoneMapDto;
import com.example.is_curse_work.model.Fridge;
import com.example.is_curse_work.model.FridgeMembership;

import java.util.List;

public interface FridgeService {
    List<Fridge> listFridges();
    void joinFridge(Long fridgeId, Long userId);
    List<ZoneMapDto> getMap(Long fridgeId);

    List<FridgeMembership> listMembers(Long fridgeId);
    void removeMember(Long fridgeId, Long userId);
}

