package com.legacy.application.mng.user.mapper;

import com.legacy.application.mng.user.vo.UserMngVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;


import java.util.List;

/**
 * 관리자 사용자 관리 Mapper
 */
@Mapper("userMngMapper")
public interface UserMngMapper {

    /**
     * 회원 리스트 ( FIXME ( 데이터 테이블 사용으로 페이징 x -> 필요 시, 추가 )
     * @param userMngVO
     * @return
     * @throws Exception
     */
    public List<UserMngVO> getUserList(UserMngVO userMngVO) throws Exception;

    /**
     * 유저 정보 get
     * @param userMngVO
     * @return
     * @throws Exception
     */
    UserMngVO getUserDetail(UserMngVO userMngVO) throws Exception;

    /**
     * 회원정보 등록
     * @param userMngVO
     * @return
     * @throws Exception
     */
    int setUserInfo(UserMngVO userMngVO) throws Exception;

    /**
     * 회원정보 수정
     * @param userMngVO
     * @return
     * @throws Exception
     */
    int updUserInfo(UserMngVO userMngVO) throws Exception;

    /**
     * 회원정보 삭제
     * @param userMngVO
     * @return
     * @throws Exception
     */
    int delUserInfo(UserMngVO userMngVO) throws Exception;

}