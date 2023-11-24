package com.legacy.application.site.join.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import com.legacy.application.common.system.login.vo.LoginVO;
import lombok.extern.java.Log;

@Mapper("joinMapper")
public interface JoinMapper {

    /**
     * 아이디 중복검사
     * @param vo
     * @return
     * @throws Exception
     */
    public int checkId(LoginVO vo) throws Exception;

    /**
     * 회원가입 Insert
     * @param vo
     * @return
     * @throws Exception
     */
    public int setUserInfo(LoginVO vo) throws Exception;

    /**
     * 회원 탈퇴 시, 비밀번호 확인
     * @param vo
     * @return
     * @throws Exception
     */
    int checkPwd(LoginVO vo) throws Exception;

    /**
     * 회원정보 수정
     * @param vo
     * @return
     * @throws Exception
     */
    int updUserInfo(LoginVO vo) throws Exception;

    /**
     * 회원 탈퇴 처리
     * @param vo
     * @return
     * @throws Exception
     */
    int delUserInfo(LoginVO vo) throws Exception;
}
