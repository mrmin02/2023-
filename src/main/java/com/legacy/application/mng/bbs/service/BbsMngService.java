package com.legacy.application.mng.bbs.service;

import com.legacy.application.common.system.login.vo.LoginVO;
import com.legacy.application.mng.bbs.mapper.BbsMngMapper;
import com.legacy.application.mng.bbs.vo.BbsMngVO;
import com.legacy.application.mng.user.vo.UserMngVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("bbsMngService")
public class BbsMngService {

    @Resource(name = "bbsMngMapper")
    BbsMngMapper bbsMngMapper;

    /**
     * 게시판 정보 리스트
     * @param bbsMngVO
     * @return
     * @throws Exception
     */
    public List<BbsMngVO> getBbsList(BbsMngVO bbsMngVO) throws Exception{
        return bbsMngMapper.getBbsList(bbsMngVO);
    }

    /**
     * 게시판 코드 중복체크
     * @param bbsMngVO
     * @return
     * @throws Exception
     */
    public Map<String,Object> checkBbsCd(BbsMngVO bbsMngVO) throws Exception{
        Map<String, Object> rtnMap = new HashMap<>();
        boolean result = false;
        String rMsg = "";

        try{
            // 게시판 코드 중복 체크
            result = bbsMngMapper.checkBbsCd(bbsMngVO) == 0;

            if(result){
                rMsg = "사용가능한 코드";
            }else{
                rMsg = "이미 사용중인 게시판 코드입니다.";
            }
        }catch (Exception e){
            e.printStackTrace();
            result = false;
            rMsg = "게시판 코드 중복 확인 중 오류가 발생했습니다.";
        }

        rtnMap.put("result",result);
        rtnMap.put("rMsg",rMsg);
        return rtnMap;
    }

    /**
     * 게시판 detail get
     * @param bbsMngVO
     * @return
     * @throws Exception
     */
    public Map<String,Object> getBbsDetail(BbsMngVO bbsMngVO) throws Exception{
        Map<String,Object> rtnMap = new HashMap<>();
        boolean result = false;
        String rMsg = "";
        try {
            BbsMngVO info = bbsMngMapper.getBbsDetail(bbsMngVO);
            if(info != null){
                result = true;
                rtnMap.put("value", info);
            }else{
                rMsg = "게시판 정보가 없습니다.";
            }

        }catch (Exception e){
            e.printStackTrace();
            rMsg = "게시판 정보 검색 중 오류가 발생했습니다.";
        }

        rtnMap.put("rMsg",rMsg);
        rtnMap.put("result",result);

        return rtnMap;
    }

    /**
     * 게시판 추가/수정/삭제
     * @param bbsMngVO
     * @return
     * @throws Exception
     */
    public Map<String,Object> bbsProc(BbsMngVO bbsMngVO) throws Exception{
        Map<String,Object> rtnMap = new HashMap<>();
        boolean result = false;
        String rMsg = "";
        String redirectUrl = "";
        boolean check = false;
        try{
            if(bbsMngVO.getFlag() != null){
                switch (bbsMngVO.getFlag()){
                    case "c": // 생성
                        check = bbsMngMapper.checkBbsCd(bbsMngVO) == 0; // 게시판 코드 중복확인
                        if(check){
                            result = bbsMngMapper.setBbsInfo(bbsMngVO) > 0; //게시판 정보 입력
                            if(result){
                                // TODO 게시판 추가 정보 입력

                                result = bbsMngMapper.setBbsDetail(bbsMngVO) > 0;

                                if(result){
                                    rMsg = "게시판 등록이 완료되었습니다.";
                                    redirectUrl = "/mng/bbs/form?flag=u&bbs_info_seq="+bbsMngVO.getBbs_info_seq();;
                                }else{
                                    rMsg = "게시판 상세정보 등록에 실패하였습니다.";
                                    // 게시판 정보 물리 삭제
                                    bbsMngMapper.delRealBbsInfo(bbsMngVO);
                                }
                            }else{
                                rMsg = "게시판 등록에 실패하였습니다.";
                            }
                        }else{
                            rMsg = "이미 존재하는 게시판 코드입니다.";
                        }
                        break;
                    case "u": // 수정
                        if(bbsMngVO.getBbs_cd_cg().equals("Y")){ // 게시판 코드 변경 시,
                            check = bbsMngMapper.checkBbsCd(bbsMngVO) == 0; // 게시판 코드 중복확인

                        }else{
                            check = true;
                        }
                        if(check) {
                            result = bbsMngMapper.updBbsInfo(bbsMngVO) > 0; // 게시판 정보 수정
                            if(result){
                                // 게시판 상세정보 수정
                                result = bbsMngMapper.updBbsDetail(bbsMngVO) > 0;

                                if(result){
                                    rMsg = "게시판 수정이 완료되었습니다.";
                                    redirectUrl = "/mng/bbs/form?flag=u&bbs_info_seq="+bbsMngVO.getBbs_info_seq();
                                }else{
                                    rMsg = "게시판 상세정보 수정에 실패하였습니다. 다시 시도해주세요.";
                                }
                            }else{
                                rMsg = "게시판 수정에 실패하였습니다.";
                            }
                        }else{
                            rMsg = "이미 존재하는 게시판 코드입니다.";
                        }
                        break;
                    case "d": // 삭제
                        result = bbsMngMapper.delBbsInfo(bbsMngVO) > 0; // 게시판 정보 삭제 ( flag )
                        if(result){
                            rMsg = "게시판 삭제가 완료되었습니다.";
                            redirectUrl = "/mng/bbs/list/";
                        }else{
                            rMsg = "게시판 삭제에 실패하였습니다.";
                        }
                        break;
                    default:
                        rMsg = "잘못된 요청입니다. (code:bbsMngInfoFlag01)";
                        break;
                }
            }else{
                rMsg = "잘못된 요청입니다. (code:bbsMngInfoFlag00)";
            }
        }catch (Exception e){
            e.printStackTrace();
            result = false;
            rMsg = "게시판 정보 처리중 오류가 발생했습니다.";
        }

        rtnMap.put("result",result);
        rtnMap.put("rMsg", rMsg);
        rtnMap.put("redirectUrl", redirectUrl);

        return rtnMap;
    }
}
