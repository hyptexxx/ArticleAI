package com.example.ArticleAI.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum UserRoleEnum {
    EMPLOYEE(4, "EMPLOYEE"),
    STUDENT(2, "STUDENT"),
    ADMIN(1, "ADMIN"),
    ANONYMOUS;

    private Integer userType;
    private String roleName;

    public static UserRoleEnum getByUserType(Integer userType) {
        return Arrays.stream(UserRoleEnum.values())
                .filter(userRoleEnum -> userRoleEnum.userType.equals(userType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}