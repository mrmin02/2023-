package com.legacy.application.mng.html.service;

import com.legacy.application.common.system.login.vo.LoginVO;
import com.legacy.application.mng.html.mapper.HtmlMngMapper;
import com.legacy.application.mng.html.vo.HtmlMngVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("htmlMngService")
public class HtmlMngService {

    @Resource(name = "htmlMngMapper")
    HtmlMngMapper htmlMngMapper;

    /**
     * html 리스트
     * @param htmlMngVO
     * @return
     * @throws Exception
     */
    public List<HtmlMngVO> getHtmlList(HtmlMngVO htmlMngVO) throws Exception{
        return htmlMngMapper.getHtmlList(htmlMngVO);
    }

    /**
     * html 상세정보
     * @param htmlMngVO
     * @return
     * @throws Exception
     */
    public Map<String, Object> getHtml(HtmlMngVO htmlMngVO) throws Exception{

        Map<String,Object> rtnMap = new HashMap<>();
        String rMsg = "";
        boolean result = false;

        if(!StringUtils.isEmpty(htmlMngVO.getHtml_seq())){
            HtmlMngVO vo = htmlMngMapper.getHtml(htmlMngVO);
            if(vo != null){
                result = true;
                rtnMap.put("html",vo);
            }else{
                rMsg = "html 정보가 없습니다.";
            }
        }else{
            result = true;
            rtnMap.put("html",htmlMngVO);
        }



        rtnMap.put("result", result);
        rtnMap.put("rMsg",rMsg);
        return rtnMap;
    }

    /**
     * html proc
     * @param htmlMngVO
     * @return
     * @throws Exception
     */
    public Map<String, Object> htmlProc(HtmlMngVO htmlMngVO) throws Exception{
        Map<String,Object> rtnMap = new HashMap<>();
        String rMsg = "";
        boolean result = false;
        String redirectUrl = "";
        try{
            if(htmlMngVO.getFlag() != null){
                switch (htmlMngVO.getFlag()){
                    case "c": // 생성
                        result = htmlMngMapper.setHtml(htmlMngVO) > 0;
                        if(result){
                            rMsg = "HTML 등록이 완료되었습니다.";
                            redirectUrl = "/mng/html/detail/"+htmlMngVO.getHtml_seq();
                        }else{
                            rMsg = "HTML 등록에 실패하였습니다.";
                        }

                        break;
                    case "u": // 수정
                        result = htmlMngMapper.updHtml(htmlMngVO) > 0;
                        if(result){
                            rMsg = "HTML 수정이 완료되었습니다.";
                            redirectUrl = "/mng/html/detail/"+htmlMngVO.getHtml_seq();
                        }else{
                            rMsg = "HTML 수정에 실패하였습니다.";
                        }
                        break;
                    case "d": // 삭제
                        result = htmlMngMapper.delHtml(htmlMngVO) > 0;
                        if(result){
                            rMsg = "HTML 삭제가 완료되었습니다.";
                            redirectUrl = "/mng/html/list/";
                        }else{
                            rMsg = "HTML 삭제에 실패하였습니다.";
                        }
                        break;
                    default:
                        rMsg = "잘못된 요청입니다. (code:HTMLMngFlag01)";
                        break;
                }
            }else{
                rMsg = "잘못된 요청입니다. (code:HTMLMngFlag00)";
            }
        }catch (Exception e){
            e.printStackTrace();
            result = false;
            rMsg = "HTML 정보 처리중 오류가 발생했습니다.";
        }

        rtnMap.put("result",result);
        rtnMap.put("rMsg", rMsg);
        rtnMap.put("redirectUrl", redirectUrl);
        return rtnMap;
    }
}
