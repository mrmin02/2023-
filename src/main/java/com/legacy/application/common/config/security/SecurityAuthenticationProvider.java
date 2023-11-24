package com.legacy.application.common.config.security;

import com.legacy.application.common.system.login.service.LoginService;
import com.legacy.application.common.system.login.vo.LoginVO;
import com.legacy.application.common.util.EncryptUtil;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 로그인 인증 처리
 */
@Component
public class SecurityAuthenticationProvider implements AuthenticationProvider {

    @Resource(name = "loginService")
    LoginService loginService;

    @Override
    public boolean supports(Class<?> authentication){
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    /**
     * 로그인 인증처리
     *
     * ERR01 = 아이디 혹은 비밀번호 틀림
     * ERR02 = 탈퇴된 회원
     * @param authentication the authentication request object.
     *
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken result = null;

        // 로그인 인증처리

        String user_id = (String) authentication.getPrincipal();
        String user_pwd = (String) authentication.getCredentials();
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();

        LoginVO vo = new LoginVO();
        vo.setUser_id(user_id);
        vo.setUser_pwd(user_pwd);

        try{
            LoginVO loginVO = new LoginVO();
            vo.setUser_pwd(EncryptUtil.fn_encryptSHA256(user_pwd));

            loginVO = loginService.getUser(vo);

            if(loginVO == null){
                // 사용자 정보 없음
                // 아이디 혹은 비밀번호 틀림
                throw new BadCredentialsException("err01");
            }else{
                if(loginVO.getDel_yn().equals("Y")){
                    // 탈퇴된 회원
                    throw new BadCredentialsException("err02");
                }
                // 로그인 정상 처리
                roles.add(new SimpleGrantedAuthority(loginVO.getUser_auth()));
                result = new UsernamePasswordAuthenticationToken(user_id, user_pwd, roles);
                result.setDetails(loginVO);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BadCredentialsException(e.getMessage());
        }
        return result;
    }
}
