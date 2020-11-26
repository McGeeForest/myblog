package com.foresttech.myblog.Config.Security.Util.Handler;

import com.foresttech.myblog.Config.Security.Util.Manger.JwtTokenManager;
import com.foresttech.myblog.Platform.Model.User;
import com.foresttech.myblog.Utils.Message;
import com.foresttech.myblog.Utils.ResponseHead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class EmailPasswordLoginFailureHandler implements AuthenticationFailureHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public EmailPasswordLoginFailureHandler() {

    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        PrintWriter writer = httpServletResponse.getWriter();
        Message message = new Message();
        message.setResHead(ResponseHead.USER_LOGIN_ERROR).setResData("通过 email 登录失败").setResToken("");
        writer.write(message.toJsonString());
        logger.info(" 通过 email 登录失败："+message.toJsonString());
    }
}
