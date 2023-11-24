package com.legacy.application.mng.user.vo;

import com.legacy.application.common.util.EncryptUtil;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class UserMngVO {

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
    private String flag; // 생성 수정 플레그

    public UserMngVO(){}
    public UserMngVO(String user_seq){
        this.user_seq = user_seq;
    }

    public void setUser_pwd(String user_pwd){
        if(!StringUtils.isEmpty(user_pwd)){
            this.user_pwd = EncryptUtil.fn_encryptSHA256(user_pwd);
        }
    }
}
