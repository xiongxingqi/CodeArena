package com.celest.backend.config.filter;

import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.celest.backend.config.properties.JwtProperties;
import com.celest.backend.mapper.UserMapper;
import com.celest.backend.pojo.entity.User;
import com.celest.backend.service.impl.utils.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationTokenFilter extends OncePerRequestFilter {
    private final UserMapper userMapper;

    private final JwtProperties jwtProperties;

    /**
     * 用于处理已认证过请求的jwt,并获得用户的上下文
     *
     * @param request http请求
     * @param response http响应
     * @param filterChain 调用链
     */
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasText(token)|| !token.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }
        token = token.substring(7);
        String userId;

        if (!JWTUtil.verify(token,jwtProperties.getSecretKey().getBytes())) {
            filterChain.doFilter(request,response);
            return;
        }
        JWT jwt = JWTUtil.parseToken(token);
        JSONObject claimsJson = jwt.getPayload().getClaimsJson();
        //todo 令牌有效期校验
        userId = claimsJson.get("uid",String.class);

        User loginUser = userMapper.selectById(Integer.parseInt(userId));
        UserDetailsImpl userDetails = new UserDetailsImpl(loginUser);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, null);
        SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
        emptyContext.setAuthentication(usernamePasswordAuthenticationToken);
        SecurityContextHolder.setContext(emptyContext);

        filterChain.doFilter(request,response);
    }
}
