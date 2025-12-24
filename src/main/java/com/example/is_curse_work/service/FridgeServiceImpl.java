package com.example.is_curse_work.service;

import com.example.is_curse_work.dto.ZoneMapDto;
import com.example.is_curse_work.model.Fridge;
import com.example.is_curse_work.model.FridgeMembership;
import com.example.is_curse_work.repository.FridgeMembershipRepository;
import com.example.is_curse_work.repository.FridgeRepository;
import com.example.is_curse_work.repository.function.FridgeMembershipFunctionRepository;
import com.example.is_curse_work.repository.function.MapFunctionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FridgeServiceImpl implements FridgeService {

    private final FridgeRepository fridges;
    private final FridgeMembershipRepository memberships;
    private final FridgeMembershipFunctionRepository membershipFn;
    private final MapFunctionRepository mapFn;

    public FridgeServiceImpl(FridgeRepository fridges,
                             FridgeMembershipRepository memberships,
                             FridgeMembershipFunctionRepository membershipFn,
                             MapFunctionRepository mapFn) {
        this.fridges = fridges;
        this.memberships = memberships;
        this.membershipFn = membershipFn;
        this.mapFn = mapFn;
    }

    @Override
    public List<Fridge> listFridges() {
        return fridges.findAll();
    }

    @Override
    @Transactional
    public void joinFridge(Long fridgeId, Long userId) {
        membershipFn.addMember(fridgeId, userId, false);
    }

    @Override
    public List<ZoneMapDto> getMap(Long fridgeId) {
        return mapFn.getFridgeMap(fridgeId);
    }

    @Override
    public List<FridgeMembership> listMembers(Long fridgeId) {
        return memberships.findByFridgeIdAndLeftAtIsNull(fridgeId);
    }

    @Override
    @Transactional
    public void removeMember(Long fridgeId, Long userId) {
        membershipFn.removeMember(fridgeId, userId);
    }
}

