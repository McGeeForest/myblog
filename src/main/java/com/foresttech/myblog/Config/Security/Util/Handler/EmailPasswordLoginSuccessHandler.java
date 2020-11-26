package com.foresttech.myblog.Config.Security.Util.Handler;

import com.foresttech.myblog.Config.Security.Util.Manger.JwtTokenManager;
import com.foresttech.myblog.Platform.Model.User;
import com.foresttech.myblog.Utils.Message;
import com.foresttech.myblog.Utils.ResponseHead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class EmailPasswordLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final JwtTokenManager tokenManager;

    public EmailPasswordLoginSuccessHandler(JwtTokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        String token = tokenManager.createToken(user.getUsername());
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setMaxAge(3600);     //秒数，如果为复数则cookie存放在客户端内存中的时间，浏览器关闭，内存释放cookie则被清理
        httpServletResponse.addCookie(cookie);
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter writer = httpServletResponse.getWriter();
        Message message = new Message();
        message.setResHead(ResponseHead.SUCCESS).setResToken(token);
        writer.write(message.toJsonString());
        logger.info(user.getUsername()+" 通过 email 登录成功，发放token于cookie："+message.toJsonString());
    }
}
