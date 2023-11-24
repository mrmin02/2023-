package com.legacy.application.site.join.control;

import com.legacy.application.common.system.login.vo.LoginVO;
import com.legacy.application.site.join.service.JoinService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class JoinController {
    @Resource(name = "joinService")
    JoinService joinService;

    /**
     * 회원가입 정책 페이지
     * @param model
     * @param request
     * @param response
     * @param redirectAttributes
     * @param principal
     * @return
     */
    @RequestMapping("/join/policy")
    public String joinIndex(Model model
                , HttpServletRequest request
                , HttpServletResponse response
                , RedirectAttributes redirectAttributes
                , HttpSession session
                , Principal principal) throws Exception {

        if(principal != null){
            redirectAttributes.addFlashAttribute("rMsg","이미 로그인되어 있습니다.");
            return "redirect:/main";
        }

        /**
         * url 로 회원가입 form 진입 접근 방지용
         */
        session.setAttribute("joinPolicy","check");
        return "site/join/policy";
    }

    /**
     * 회원가입  form
     * @param model
     * @param request
     * @param response
     * @param redirectAttributes
     * @param principal
     * @return
     */
    @RequestMapping("/join/form")
    public String joinForm(@ModelAttribute("loginVO") LoginVO loginVO
            , Model model
            , HttpServletRequest request
            , HttpServletResponse response
            , RedirectAttributes redirectAttributes
            , HttpSession session
            , Principal principal) throws Exception {

        /**
         * 이미 로그인 되어 있으면 redirect
         */
        if(principal != null){
            redirectAttributes.addFlashAttribute("rMsg","이미 로그인되어 있습니다.");
            return "redirect:/main";
        }
        /**
         * url 로 회원가입 form 진입 접근 방지용
         */
//        if(session.getAttribute("joinPolicy") == null || !session.getAttribute("joinPolicy").equals("check")){
//            redirectAttributes.addFlashAttribute("rMsg","회원가입 정책 동의 후 이용해주세요.");
//            return "redirect:/main";
//        }

        session.removeAttribute("joinPolicy");
        return "site/join/form";
    }

    /**
     * 회원가입  아이디 중복 체크
     * @return
     */
    @ResponseBody
    @RequestMapping("/ajax/join/check")
    public Map<String, Object> joinForm(@ModelAttribute("loginVO") LoginVO loginVO) throws Exception {

        Map<String, Object> rtnMap = new HashMap<>();

        rtnMap = joinService.checkId(loginVO);

        return rtnMap;
    }

    /**
     * 회원가입  process
     * @param loginVO
     * @param model
     * @param request
     * @param response
     * @param redirectAttributes
     * @param principal
     * @return
     */
    @RequestMapping("/join/proc")
    public String joinProc( @ModelAttribute("loginVO") LoginVO loginVO
            , Model model
            , HttpServletRequest request
            , HttpServletResponse response
            , RedirectAttributes redirectAttributes
            , Principal principal) throws Exception {

        if(principal != null){
            redirectAttributes.addFlashAttribute("rMsg","이미 로그인되어 있습니다.");
            return "redirect:/main";
        }

        // TODO 파라메터 검사

        Map<String, Object> rtnMap = joinService.joinProc(loginVO);


        if((boolean)rtnMap.get("result")){
            /**
             * 회원가입 완료
             */
            return "redirect:/join/complete";
        }else{
            /**
             * 회원가입 도중 예외상황 발생
             */
            redirectAttributes.addFlashAttribute("rMsg",rtnMap.get("rMsg"));
            return "redirect:/main";
        }

    }
    @RequestMapping("/join/complete")
    public String joinComplete( @ModelAttribute("loginVO") LoginVO loginVO
            , Model model
            , HttpServletRequest request
            , HttpServletResponse response
            , RedirectAttributes redirectAttributes
            , Principal principal) throws Exception {


        return "site/join/complete";
    }

    /**
     * 마이페이지 form
     * @param loginVO
     * @param model
     * @param request
     * @param response
     * @param redirectAttributes
     * @param principal
     * @return
     */
    @RequestMapping("/mypage/form")
    public String mypageForm( @ModelAttribute("loginVO") LoginVO loginVO
            , Model model
            , HttpServletRequest request
            , HttpServletResponse response
            , RedirectAttributes redirectAttributes
            , Principal principal) throws Exception {
        if(principal == null){
            redirectAttributes.addFlashAttribute("rMsg","로그인 후 이용가능합니다.");
            return "redirect:/main";
        }

        return "site/join/mypage";
    }

    /**
     * 회원탈퇴 폼 form
     * @param loginVO
     * @param model
     * @param request
     * @param response
     * @param redirectAttributes
     * @param principal
     * @return
     */
    @RequestMapping("/mypage/del_form")
    public String mypageDelForm( @ModelAttribute("loginVO") LoginVO loginVO
            , Model model
            , HttpServletRequest request
            , HttpServletResponse response
            , RedirectAttributes redirectAttributes
            , Principal principal) throws Exception {
        if(principal == null){
            redirectAttributes.addFlashAttribute("rMsg","로그인 후 이용가능합니다.");
            return "redirect:/main";
        }

        return "site/join/del_form";
    }

    /**
     * 회원 비밀번호 확인
     * @param loginVO
     * @param model
     * @param request
     * @param response
     * @param redirectAttributes
     * @param principal
     * @return
     */
    @ResponseBody
    @RequestMapping("/ajax/mypage/check")
    public Map<String,Object> aJaxMypageDelForm( @ModelAttribute("loginVO") LoginVO loginVO
            , Model model
            , HttpServletRequest request
            , HttpServletResponse response
            , RedirectAttributes redirectAttributes
            , Principal principal) throws Exception {

        return joinService.checkPwd(loginVO, principal);
    }

    /**
     * 마이페이지 회원정보 수정/삭제
     * @param loginVO
     * @param model
     * @param request
     * @param response
     * @param redirectAttributes
     * @param principal
     * @return
     */
    @RequestMapping("/mypage/proc")
    public String mypageProc( @ModelAttribute("loginVO") LoginVO loginVO
            , Model model
            , HttpServletRequest request
            , HttpServletResponse response
            , RedirectAttributes redirectAttributes
            , Principal principal) throws Exception {

        if(principal == null){
            redirectAttributes.addFlashAttribute("rMsg","로그인 후 이용가능합니다.");
            return "redirect:/main";
        }

        Map<String,Object> rtnMap = joinService.mypageProc(loginVO, principal);

        redirectAttributes.addFlashAttribute("rMsg",rtnMap.get("rMsg"));

        if((boolean)rtnMap.get("result")){
            if(loginVO.getFlag().equals("u")){
                // 회원정보 수정 완료
                return "redirect:/mypage/form";
            }else{
                //회원탈퇴 성공,  로그아웃 처리
                return "redirect:/logout";
            }
        }else{
            // 오류 발생
            return "redirect:/main";
        }
    }
}
