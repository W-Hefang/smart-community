package edu.xcc.smartcommunity.interceptor;

import edu.xcc.smartcommunity.entity.vo.UserLoginVO;
import edu.xcc.smartcommunity.handler.BizException;
import edu.xcc.smartcommunity.mapper.UserMapper;
import edu.xcc.smartcommunity.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.List;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // ========== 关键：放行所有 OPTIONS 请求 ==========
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        String requestURI = request.getRequestURI();
        System.out.println("requestURI: " + requestURI);

        String authorization = request.getHeader("Authorization");
        if (authorization == null) {
            throw new BizException("TOKEN不能为空");
        }
        String rawToken = authorization.substring(7);
        boolean verify = JwtUtil.verify(rawToken);
        if (!verify) {
            throw new BizException("TOKEN校验不通过，或已过期,请重新登录");
        }

        String phone = JwtUtil.getClaim(rawToken, "phone");
        UserLoginVO vo = userMapper.getMenuListByPhone(phone);
        List<String> urlList = new ArrayList<>();
        if (vo != null && vo.getMenuList() != null) {
            vo.getMenuList().forEach(menu -> {
                if (menu.getUrl() != null) {
                    urlList.add(menu.getUrl());
                }
            });
        }

        // 前缀匹配
        boolean hasPermission = false;
        for (String allowedUrl : urlList) {
            if (requestURI.startsWith(allowedUrl)) {
                hasPermission = true;
                break;
            }
        }

        if (!hasPermission) {
            throw new BizException("未经授权的访问");
        }
        return true;
    }
}