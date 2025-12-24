package com.example.is_curse_work.repository.function;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FridgeMembershipFunctionRepositoryImpl implements FridgeMembershipFunctionRepository {

    private final JdbcTemplate jdbc;

    public FridgeMembershipFunctionRepositoryImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void addMember(Long fridgeId, Long userId, boolean moderator) {
        jdbc.update("select fn_add_fridge_member(?, ?, ?)", fridgeId, userId, moderator);
    }

    @Override
    public void removeMember(Long fridgeId, Long userId) {
        jdbc.update("select fn_remove_fridge_member(?, ?)", fridgeId, userId);
    }
}
