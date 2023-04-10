package com.alurkerja.config;

import com.alurkerja.annotation.Secured;
import com.alurkerja.constant.ApplicationEnum;
import com.alurkerja.constant.CurrentUser;
import com.alurkerja.exception.ResourceForbiddenException;
import com.alurkerja.exception.ResourceNotFoundException;
import com.alurkerja.exception.UnauthorizedException;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private final CurrentUser currentUser;

    @Autowired
    Environment env;

    @Autowired
    public TokenInterceptor(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        String userId = request.getHeader("userId");
        String name = request.getHeader("name");
        String role = request.getHeader("role");

        if (StringUtils.isAnyEmpty(userId, name, role)) {
            throw ResourceForbiddenException.create("Unauthorized Access");
        }
        if (!(handler instanceof HandlerMethod)) {
            throw ResourceNotFoundException.create("Invalid url");
        }
        HandlerMethod method = (HandlerMethod) handler;
        Secured anno = method.getMethodAnnotation(Secured.class);

        boolean allowed;
        try {
            ApplicationEnum.Group access = ApplicationEnum.Group.valueOf(role);
            allowed = anno == null || anno.value().length == 0 || Arrays.asList(anno.value()).contains(access);
        } catch (Exception e) {
            throw UnauthorizedException.create("Invalid Role");
        }

        if (!allowed) {
            throw ResourceForbiddenException.create("Forbidden Access");
        }

        currentUser.set(userId, name, role);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
