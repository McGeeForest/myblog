package com.foresttech.myblog.Config.Security.Util.Filter;

import com.foresttech.myblog.Config.Security.Util.Manger.JwtTokenManager;
import com.foresttech.myblog.Utils.Message;
import com.foresttech.myblog.Utils.ResponseHead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TokenAuthenticationFilter extends BasicAuthenticationFilter {
    Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);
    private final JwtTokenManager tokenManager;

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenManager tokenManager) {
        super(authenticationManager);
        this.tokenManager = tokenManager;
    }

//    这里的Filter相当于security的UsernamePasswordAuthenticationFilter+Provider+Handler，最后要获得用户的权限是否正常以及写入response
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("token");
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                }
            }
        }
        if (token == null) {
            chain.doFilter(request, response);
            return;
        }
        logger.info("获得请求token："+token);
        try {
//            throw new NullPointerException("测试拦截。");
            UsernamePasswordAuthenticationToken authentication = getAuthentication(token);
            if (authentication != null) {
//                 设置认证，需要携带权限信息
                SecurityContextHolder.getContext().setAuthentication(authentication);
//                更新token信息，写入cookie
                String newtoken = tokenManager.createToken(tokenManager.getUserFromToken(token));
                Cookie cookie = new Cookie("token", newtoken);
                cookie.setPath("/");
                cookie.setMaxAge(3600);     //秒数，如果为复数则cookie存放在客户端内存中的时间，浏览器关闭，内存释放cookie则被清理
                response.addCookie(cookie);
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json; charset=utf-8");
            }
            chain.doFilter(request, response);
        }
        catch (Exception e) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            Message message = new Message();
            message.setResHead(ResponseHead.USER_LOGIN_EXPIRED);
            message.setResData(e.getMessage());
            writer.write(message.toJsonString());
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (token != null && !"".equals(token.trim())) {
            String userName = tokenManager.getUserFromToken(token);
            if (!StringUtils.isEmpty(userName)) {
                return new UsernamePasswordAuthenticationToken(userName, token, new ArrayList<>());
            }
            return null;
        }
        return null;
    }

}
