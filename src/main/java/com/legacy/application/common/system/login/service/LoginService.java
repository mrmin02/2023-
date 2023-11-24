package com.legacy.application.common.system.login.service;

import com.legacy.application.common.system.login.mapper.LoginMapper;
import com.legacy.application.common.system.login.vo.LoginVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("loginService")
public class LoginService {

    @Resource(name = "loginMapper")
    LoginMapper loginMapper;

    public LoginVO getUser(LoginVO vo) throws Exception{
        return loginMapper.getUser(vo);
    }
}
