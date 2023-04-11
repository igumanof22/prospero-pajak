package com.alurkerja.common.service;

import com.alurkerja.common.entity.User;
import com.alurkerja.core.util.HttpUtil;
import com.alurkerja.core.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class UserService {
    private final String userApiServer;
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    @Autowired
    public UserService(Environment environment) {
        userApiServer = environment.getProperty("user.api.endpoint");
    }

    public User findByEmail(String email) {
        if (email == null) {
            return null;
        }
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("userId", "SYSTEM");
            headers.put("name", "SYSTEM");
            headers.put("role", "ADMIN");
            byte[] response = HttpUtil.get(userApiServer + "/find?email=" + email, headers);
            return JsonUtil.deserializeIgnoreUnknown(new String(response), User.class);
        } catch (Exception e) {
            LOGGER.info(email);
            LOGGER.warning(e.getMessage());
            return null;
        }
    }
}
