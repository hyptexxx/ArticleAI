package com.example.ArticleAI.repository;

import com.example.ArticleAI.dto.UtmoUserDto;
import com.example.ArticleAI.mappers.UtmoUserMapper;
import com.example.ArticleAI.models.UtmoUser;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public boolean save(@NonNull UtmoUser utmoUser) {
        try {
            jdbcTemplate.update("insert into users (id, remote_pin, fio, user_type, info) values (?, ?, ?, ?, ?)",
                    utmoUser.getId(), utmoUser.getPin(), utmoUser.getFio(), utmoUser.getUser_type(), utmoUser.getInfo());
            log.info("New user saved");

            return true;
        } catch (Exception e) {
            log.info("Failed save user");
            return false;
        }
    }

    public boolean isUserRegistered(@NonNull UtmoUser utmoUser) {
        final Integer count;
        try {
            count = jdbcTemplate.queryForObject("select count(*) from users where id = ?",
                    Integer.class, utmoUser.getId());
        } catch (Exception e) {
            log.info("Failed found user. There is an error: {}", e.getMessage());
            return false;
        }

        if (count == null) {
            return false;
        }

        if (count == 1) {
            log.info("User founded remote_id: {}", utmoUser.getId());
            return true;
        }

        if (count > 1) {
            log.info("There is more than one saved user for remote_id: {}", utmoUser.getId());
            throw new AccessDeniedException("There is more than one saved user for remote_id: " + utmoUser.getId());
        }

        log.info("Fail to found user with remote_id: {}", utmoUser.getId());
        return false;
    }

    public UtmoUserDto getUserById(@NonNull Integer userId) {
        return jdbcTemplate.queryForObject("select * from users where id = ?", new UtmoUserMapper(), userId);
    }
}
