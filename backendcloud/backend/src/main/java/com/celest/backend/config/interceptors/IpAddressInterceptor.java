package com.celest.backend.config.interceptors;


import com.celest.backend.utils.IpUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class IpAddressInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,@NotNull Object handler) {

        if (! (handler instanceof HandlerMethod))  return true;

        String ip = IpUtil.getClientIpAddress(request);
        if (IpUtil.LOCALHOST_IPV4.equals(ip)) {
            return true;
        }
        response.setStatus(403);
        return false;
    }
}
