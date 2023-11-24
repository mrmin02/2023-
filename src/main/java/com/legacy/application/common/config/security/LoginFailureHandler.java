package com.legacy.application.common.config.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

@Component // 개발자 소스 Bean 으로 등록
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String REFERER = request.getHeader("referer");
        String SET_DEFAULT_FAILURE_URL = "/login"; // 로그인 페이지 기본 값

        // 관리자 페이지에서 로그인 실패한 경우
        if(REFERER.contains("mng")){
            SET_DEFAULT_FAILURE_URL = "/mng/login";
        }

        // 로그인 실패 로직
        HttpSession session = request.getSession();
        Map<String, String> rtnMap = new HashMap<>();
        String rMsg = "";
        String rHeader = "로그인에 실패하였습니다.";

        switch (exception.getMessage()){
            case "err01":
                rMsg = "아이디 혹은 비밀번호가 올바르지 않습니다.";
                break;
            case "err02":
                rMsg = "이미 탈퇴된 회원입니다.";
                break;
            default:
                rMsg = "로그인에 실패하였습니다.";
                break;
        }

        rtnMap.put("rHeader",rHeader);
        rtnMap.put("rMsg",rMsg);
        session.setAttribute("loginFailureMsg", rtnMap);

        super.setDefaultFailureUrl(SET_DEFAULT_FAILURE_URL);
        // redirect url 설정 후 부모클래스 함수 호출
        super.onAuthenticationFailure(request,response,exception);
    }
}
