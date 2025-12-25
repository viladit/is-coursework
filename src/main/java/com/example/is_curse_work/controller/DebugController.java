package com.example.is_curse_work.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class DebugController {

    private final JdbcTemplate jdbc;

    public DebugController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @GetMapping("/debug/db")
    @ResponseBody
    public Map<String, Object> debugDb() {
        Map<String, Object> out = new LinkedHashMap<>();
        out.put("current_database", jdbc.queryForObject("select current_database()", String.class));
        out.put("current_schema", jdbc.queryForObject("select current_schema()", String.class));
        out.put("current_user", jdbc.queryForObject("select current_user", String.class));
        out.put("search_path", jdbc.queryForObject("show search_path", String.class));
        out.put("email_type", jdbc.queryForObject("""
                select data_type
                from information_schema.columns
                where table_schema = current_schema()
                  and table_name = 'users'
                  and column_name = 'email'
                """, String.class));
        out.put("email_pg_type", jdbc.queryForObject("select pg_typeof(email) from users limit 1", String.class));
        try {
            out.put("lower_email", jdbc.queryForObject("select lower(email) from users limit 1", String.class));
        } catch (Exception ex) {
            out.put("lower_email_error", ex.getMessage());
        }
        return out;
    }
}
