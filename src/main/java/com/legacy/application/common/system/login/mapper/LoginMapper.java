package com.legacy.application.common.system.login.mapper;

import com.legacy.application.common.system.login.vo.LoginVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;


@Mapper("loginMapper")
public interface LoginMapper {
    public LoginVO getUser(LoginVO vo) throws Exception;
}
