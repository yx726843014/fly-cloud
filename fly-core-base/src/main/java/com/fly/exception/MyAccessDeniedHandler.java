package com.fly.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 游雄
 * @describe
 * @create 9:57 2018/10/3 0003
 */
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
            response.setContentType("application/json;charset=UTF-8");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("code", 401);//401
            map.put("msg", "权限不足");
            map.put("data", e.getMessage());
            map.put("success", false);
            map.put("path", request.getServletPath());
            map.put("timestamp", String.valueOf(new Date().getTime()));
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(mapper.writeValueAsString(map));
    }
}
