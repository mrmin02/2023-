package com.legacy.application.common.config.security;

import com.legacy.application.common.system.login.vo.LoginVO;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginVO vo = (LoginVO) authentication.getDetails();

        // 관리자 로그인 성공 시, redirect
        if(vo.getUser_auth().equals("ROLE_ADMIN")){
            getRedirectStrategy().sendRedirect(request, response, "/mng/bbs/list");
        }else{
            getRedirectStrategy().sendRedirect(request, response, "/main");
        }
    }
}
