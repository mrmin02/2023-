package com.legacy.application.site.join.service;

import com.legacy.application.common.system.login.vo.LoginVO;
import com.legacy.application.common.util.EncryptUtil;
import com.legacy.application.site.join.mapper.JoinMapper;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Service("joinService")
public class JoinService {

    @Resource(name = "joinMapper")
    JoinMapper joinMapper;

    /**
     * 아이디 중복 체크
     * @return
     * @throws Exception
     */
    public Map<String, Object> checkId(LoginVO vo) throws Exception{

        Map<String, Object> rtnMap = new HashMap<>();
        boolean result = false;
        String rMsg = "";

        boolean check = joinMapper.checkId(vo) == 0;

        if(check){
            result = true;
            rMsg = "아이디 중복확인 체크 완료!";
        }else{
            rMsg = "이미 존재하는 아이디입니다.";
        }

        rtnMap.put("result",result);
        rtnMap.put("rMsg",rMsg);

        return rtnMap;
    }

    /**
     * 회원가입 등록 Process
     * @param vo
     * @return
     * @throws Exception
     */
    public Map<String, Object> joinProc(LoginVO vo) throws Exception{
        Map<String, Object> rtnMap = new HashMap<>();
        boolean result = false;
        String rMsg = "";

        /**
         * 아이디 중복체크
         */
        boolean check = joinMapper.checkId(vo) == 0;
        if(!check){
            // 중복된 아이디 존재
            rMsg = "이미 존재하는 아이디입니다.";
        }else{
            // 회원정보 Insert
            try{
                // 비밀번호 체크 및 암호화
                if(vo.getUser_pwd() == null || vo.getUser_pwd().length() == 0){
                    result = false;
                    rMsg = "회원가입 도중 오류가 발생하였습니다. (code02)";
                }else{
                    // 비밀번호 sha256 암호화
                    vo.setUser_pwd(EncryptUtil.fn_encryptSHA256(vo.getUser_pwd()));

                    result = joinMapper.setUserInfo(vo) > 0;

                    if(result){
                        result = true;
                        rMsg = "회원가입이 완료되었습니다.";
                    }else{
                        // insert 실패
                        result = false;
                        rMsg = "회원가입 도중 오류가 발생하였습니다. (code01)";
                    }
                }
            }catch (Exception e){
                result = false;
                rMsg = "회원가입 도중 오류가 발생하였습니다. (code00)";
                e.printStackTrace();
            }

        }

        rtnMap.put("result", result);
        rtnMap.put("rMsg", rMsg);

        return rtnMap;
    }

    /**
     * 회원탈퇴 시, 비밀번호 확인
     * @param loginVO
     * @param principal
     * @return
     * @throws Exception
     */
    public Map<String, Object> checkPwd(LoginVO loginVO, Principal principal) throws Exception{
        Map<String,Object> rtnMap = new HashMap<>();
        boolean result = false;
        String rMsg = "";
        try {
            if(principal != null){

                LoginVO userSession = (LoginVO)((Authentication) principal).getDetails();

                if(loginVO.getUser_pwd() != null){
                    if(loginVO.getUser_seq().equals(userSession.getUser_seq())){
                        loginVO.setUser_pwd(EncryptUtil.fn_encryptSHA256(loginVO.getUser_pwd()));
                        result = joinMapper.checkPwd(loginVO) > 0;

                        if(result){
                            rMsg = "비밀번호 확인 성공";
                        }else{
                            rMsg = "비밀번호가 다릅니다.";
                        }
                    }else{
                        // 로그인 정보와 사용자 단에서 온 회원정보가 다를 때,
                        rMsg = "잘못된 접근입니다. (code:mypageUE)";
                    }
                }else{
                    // 비밀번호 누락
                    rMsg = "비밀번호 정보가 없습니다. 다시 시도해주세요.";
                }
            }else{
                rMsg = "로그인 후 이용가능합니다.";
            }
        }catch (Exception e){
            e.printStackTrace();
            result = false;
            rMsg = "비밀번호 확인 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.";
        }



        rtnMap.put("result",result);
        rtnMap.put("rMsg",rMsg);

        return rtnMap;
    }

    /**
     * 회원정보 수정/삭제
     * @param loginVO
     * @param principal
     * @return
     * @throws Exception
     */
    public Map<String,Object> mypageProc(LoginVO loginVO, Principal principal) throws Exception{
        Map<String, Object> rtnMap = new HashMap<>();
        boolean result = false;
        String rMsg = "";

        try {
            LoginVO userSession = (LoginVO)((Authentication) principal).getDetails();

            if(loginVO.getFlag() != null){
                if(userSession.getUser_seq().equals(loginVO.getUser_seq())){

                    if(loginVO.getUser_pwd() != null){
                        loginVO.setUser_pwd(EncryptUtil.fn_encryptSHA256(loginVO.getUser_pwd()));
                        switch (loginVO.getFlag()){
                            case "u":
                                result = joinMapper.updUserInfo(loginVO) > 0;

                                if(result){
                                    rMsg = "회원정보가 수정되었습니다.";
                                }else{
                                    rMsg = "회원정보 수정에 실패하였습니다.";
                                }
                                break;
                            case "d":
                                // 비밀번호 체크
                                result = joinMapper.checkPwd(loginVO) > 0;

                                if(result){
                                    // 회원 탈퇴 처리
                                    result = joinMapper.delUserInfo(loginVO) > 0;

                                    if(result){
                                        rMsg = "회원 탈퇴 되었습니다.";
                                    }else{
                                        rMsg = "회원 탈퇴에 실패하셨습니다.";
                                    }

                                }else{
                                    rMsg = "비밀번호가 올바르지 않습니다.";
                                }

                                break;
                        }
                    }else{
                        // 입력한 비밀번호 없음
                        rMsg = "잘못된 접근입니다. (code:mypageUPN)";
                    }
                }else{
                    // 로그인 정보와 사용자 단에서 온 회원정보가 다를 때,
                    rMsg = "잘못된 접근입니다. (code:mypageUE)";
                }
            }else{
                // flag 값 누락
                rMsg = "잘못된 접근입니다.";
            }
        }catch (Exception e){
            result = false;
            rMsg = "회원정보 수정 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.";
        }

        rtnMap.put("result",result);
        rtnMap.put("rMsg",rMsg);

        return rtnMap;
    }

}
