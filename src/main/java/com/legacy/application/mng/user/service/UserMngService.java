package com.legacy.application.mng.user.service;

import com.legacy.application.common.system.login.vo.LoginVO;
import com.legacy.application.mng.user.mapper.UserMngMapper;
import com.legacy.application.mng.user.vo.UserMngVO;
import com.legacy.application.site.join.mapper.JoinMapper;
import com.legacy.application.site.join.service.JoinService;
import com.legacy.application.site.join.vo.JoinVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userMngService")
public class UserMngService {

    @Resource(name = "userMngMapper")
    UserMngMapper userMngMapper;

    @Resource(name = "joinMapper")
    JoinMapper joinMapper;

    /**
     * 유저 리스트  FIXME ( 데이터 테이블 사용으로 페이징 x -> 필요 시, 추가 )
     * @param userMngVO
     * @return
     * @throws Exception
     */
    public List<UserMngVO> getUserList(UserMngVO userMngVO) throws Exception{
        return userMngMapper.getUserList(userMngVO);
    }

    /**
     * 유저 정보 get
     * @param userMngVO
     * @return
     * @throws Exception
     */
    public Map<String,Object> getUserDetail(UserMngVO userMngVO) throws Exception{
        Map<String,Object> rtnMap = new HashMap<>();
        boolean result = false;
        String rMsg = "";
        try {
            UserMngVO info = userMngMapper.getUserDetail(userMngVO);
            if(info != null){
                result = true;
                rtnMap.put("value", info);
            }else{
                rMsg = "회원 정보가 없습니다.";
            }

        }catch (Exception e){
            e.printStackTrace();
            rMsg = "회원 정보 검색 중 오류가 발생했습니다.";
        }

        rtnMap.put("rMsg",rMsg);
        rtnMap.put("result",result);

        return rtnMap;
    }

    /**
     * 유저 정보 생성/수정/삭제
     * @param userMngVO
     * @return
     * @throws Exception
     */
    public Map<String, Object> userInfoProc(UserMngVO userMngVO) throws Exception{
        Map<String,Object> rtnMap = new HashMap<>();
        boolean result = false;
        String rMsg = "";
        String redirectUrl = "";
        try{
            if(userMngVO.getFlag() != null){
                switch (userMngVO.getFlag()){
                    case "c": // 생성
                        LoginVO loginVO = new LoginVO(userMngVO.getUser_id());
                        boolean check = joinMapper.checkId(loginVO) == 0; // 아이디 중복확인
                        if(check){
                            result = userMngMapper.setUserInfo(userMngVO) > 0;
                            if(result){
                                rMsg = "회원정보 등록이 완료되었습니다.";
                                redirectUrl = "/mng/user/detail/"+userMngVO.getUser_seq();
                            }else{
                                rMsg = "회원정보 등록에 실패하였습니다.";
                            }
                        }else{
                            rMsg = "이미 존재하는 아이디입니다.";
                        }
                        break;
                    case "u": // 수정
                        result = userMngMapper.updUserInfo(userMngVO) > 0;
                        if(result){
                            rMsg = "회원정보 수정이 완료되었습니다.";
                            redirectUrl = "/mng/user/detail/"+userMngVO.getUser_seq();
                        }else{
                            rMsg = "회원정보 수정에 실패하였습니다.";
                        }
                        break;
                    case "d": // 삭제
                        result = userMngMapper.delUserInfo(userMngVO) > 0;
                        if(result){
                            rMsg = "회원정보 삭제가 완료되었습니다.";
                            redirectUrl = "/mng/user/list/";
                        }else{
                            rMsg = "회원정보 삭제에 실패하였습니다.";
                        }
                        break;
                    default:
                        rMsg = "잘못된 요청입니다. (code:userMngFlag01)";
                        break;
                }
            }else{
                rMsg = "잘못된 요청입니다. (code:userMngFlag00)";
            }
        }catch (Exception e){
            e.printStackTrace();
            result = false;
            rMsg = "회원정보 처리중 오류가 발생했습니다.";
        }

        rtnMap.put("result",result);
        rtnMap.put("rMsg", rMsg);
        rtnMap.put("redirectUrl", redirectUrl);

        return rtnMap;
    }
}
