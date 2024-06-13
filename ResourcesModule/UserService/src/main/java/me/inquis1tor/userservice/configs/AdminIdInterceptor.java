package me.inquis1tor.userservice.configs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.inquis1tor.userservice.exceptions.AdminRequiredException;
import org.springframework.web.context.request.FacesRequestAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.UUID;

public class AdminIdInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String adminId = request.getHeader("Administrator");

        if (adminId==null) {
            System.out.println("adminId is null");
            throw new AdminRequiredException("Administrator id is required in header");
        }

        request.setAttribute("ADMIN_ID", adminId);

        RequestContextHolder.getRequestAttributes().setAttribute("ADMIN_ID", UUID.fromString(adminId), RequestAttributes.SCOPE_REQUEST);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
