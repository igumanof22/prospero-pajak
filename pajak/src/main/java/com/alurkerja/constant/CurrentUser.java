package com.alurkerja.constant;


import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Component
@RequestScope
public class CurrentUser {
    private String userId;
    private String name;

    private String role;

    public void set(String userId, String name, String role) {
        this.userId = userId;
        this.name = name;
        this.role = role;
    }
}
