package com.legacy.application.mng.bbs.control;

import com.legacy.application.mng.bbs.service.BbsMngService;
import com.legacy.application.mng.bbs.vo.BbsMngVO;
import com.legacy.application.mng.user.vo.UserMngVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

/**
 * 관리자 - 게시판 정보 Controller
 */
@Controller
public class BbsMngController {

    @Resource(name = "bbsMngService")
    BbsMngService bbsMngService;

    /**
     * 게시판 정보 리스트
     * @param bbsMngVO
     * @param request
     * @param response
     * @param model
     * @param session
     * @param redirectAttributes
     * @param principal
     * @return
     */
    @RequestMapping("/mng/bbs/list")
    public String bbsList(@ModelAttribute("bbsMngVO") BbsMngVO bbsMngVO,
                           HttpServletRequest request,
                           HttpServletResponse response,
                           Model model,
                           HttpSession session,
                           RedirectAttributes redirectAttributes,
                           Principal principal) throws Exception {

        model.addAttribute("list",bbsMngService.getBbsList(bbsMngVO));

        return "mng/bbs/list";
    }

    /**
     * 게시판 상세정보
     * @param bbsMngVO
     * @param request
     * @param response
     * @param model
     * @param session
     * @param redirectAttributes
     * @param principal
     * @return
     */
    @RequestMapping("/mng/bbs/form")
    public String bbsForm(@ModelAttribute("bbsMngVO") BbsMngVO bbsMngVO,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          Model model,
                          HttpSession session,
                          RedirectAttributes redirectAttributes,
                          Principal principal) throws Exception {
        if(bbsMngVO.getFlag() != null && bbsMngVO.getFlag().equals("u")){
            // 게시판 정보
            Map<String,Object> rtnMap = bbsMngService.getBbsDetail(bbsMngVO);

            if((boolean)rtnMap.get("result")){
                model.addAttribute("bbsMngVO",rtnMap.get("value"));
            }else{
                redirectAttributes.addFlashAttribute("rMsg",rtnMap.get("rMsg"));
                return "redirect:/mng/bbs/list";
            }
        }else{
            model.addAttribute("bbsMngVO", new BbsMngVO());
        }

        return "mng/bbs/form";
    }

    /**
     * 게시판 코드 체크
     * @param bbsMngVO
     * @param request
     * @param response
     * @param model
     * @param session
     * @param redirectAttributes
     * @param principal
     * @return
     */
    @ResponseBody
    @RequestMapping("/mng/ajax/bbs/check")
    public Map<String,Object> bbsCheck(@ModelAttribute("bbsMngVO") BbsMngVO bbsMngVO,
                                   HttpServletRequest request,
                                   HttpServletResponse response,
                                   Model model,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes,
                                   Principal principal) throws Exception {

        return bbsMngService.checkBbsCd(bbsMngVO);
    }

    /**
     * 게시판 정보 생성/수정/삭제
     * @param bbsMngVO
     * @param request
     * @param response
     * @param model
     * @param session
     * @param redirectAttributes
     * @param principal
     * @return
     */
    @RequestMapping("/mng/bbs/proc")
    public String bbsProc(@ModelAttribute("bbsMngVO") BbsMngVO bbsMngVO,
                           HttpServletRequest request,
                           HttpServletResponse response,
                           Model model,
                           HttpSession session,
                           RedirectAttributes redirectAttributes,
                           Principal principal) throws Exception {


        Map<String,Object> rtnMap = bbsMngService.bbsProc(bbsMngVO);
        redirectAttributes.addFlashAttribute("rMsg",rtnMap.get("rMsg"));

        if((boolean)rtnMap.get("result")){
            return "redirect:" + rtnMap.get("redirectUrl");
        }else{
            return "redirect:/mng/bbs/list";
        }
    }

}
