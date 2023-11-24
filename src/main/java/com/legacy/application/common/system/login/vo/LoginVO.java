package com.legacy.application.common.system.login.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 로그인 VO
 * @author mrmin
 *
 */
@Data
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class LoginVO {

    private String user_seq; // 회원 시퀀스
    private String user_id; // 회원 아이디
    private String user_pwd; // 회원 pwd
    private String user_name; // 회원 이름
    private String del_yn; // 탈퇴 여부 ( N , Y )
    private String inpt_date; // 가입일
    private String upd_date; // 수정일
    private String user_auth; // 사용자 권한

    /**
     * 로직 관련
     */
    private String flag;
    private String user_pwd_re; // 비밀번호 확인

    public LoginVO(){}
    public LoginVO(String user_id){
        this.user_id = user_id;
    }

}
