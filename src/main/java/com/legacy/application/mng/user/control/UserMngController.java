package com.legacy.application.mng.user.control;

import com.legacy.application.common.system.login.vo.LoginVO;
import com.legacy.application.mng.user.service.UserMngService;
import com.legacy.application.mng.user.vo.UserMngVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

/**
 * 관리자 사용자 컨트롤러
 */
@Controller
public class UserMngController {

    @Resource(name = "userMngService")
    UserMngService userMngService;

    /**
     * 유저 리스트
     * @param userMngVO
     * @param request
     * @param response
     * @param model
     * @param session
     * @param redirectAttributes
     * @param principal
     * @return
     */
    @RequestMapping("/mng/user/list")
    public String userList(@ModelAttribute("userMngVO") UserMngVO userMngVO,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        Model model,
                        HttpSession session,
                        RedirectAttributes redirectAttributes,
                        Principal principal) throws Exception {

        model.addAttribute("list",userMngService.getUserList(userMngVO));

        return "mng/user/list";
    }

    /**
     * 유저 리스트
     * @param userMngVO
     * @param request
     * @param response
     * @param model
     * @param session
     * @param redirectAttributes
     * @param principal
     * @return
     */
    @RequestMapping("/mng/user/detail/{user_seq}")
    public String userDetail(@ModelAttribute("userMngVO") UserMngVO userMngVO,
                        @PathVariable("user_seq") String user_seq,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        Model model,
                        HttpSession session,
                        RedirectAttributes redirectAttributes,
                        Principal principal) throws Exception {

        Map<String,Object> rtnMap = userMngService.getUserDetail(new UserMngVO(user_seq));

        if((boolean)rtnMap.get("result")){
            model.addAttribute("userMngVO",rtnMap.get("value"));
        }else{
            redirectAttributes.addFlashAttribute("rMsg",rtnMap.get("rMsg"));
            return "redirect:/mng/user/list";
        }

        return "mng/user/detail";
    }

    /**
     * 유저 form
     * @param userMngVO
     * @param request
     * @param response
     * @param model
     * @param session
     * @param redirectAttributes
     * @param principal
     * @return
     */
    @RequestMapping("/mng/user/form")
    public String userForm(@ModelAttribute("userMngVO") UserMngVO userMngVO,
                             HttpServletRequest request,
                             HttpServletResponse response,
                             Model model,
                             HttpSession session,
                             RedirectAttributes redirectAttributes,
                             Principal principal) throws Exception {

        if(userMngVO.getFlag() != null && userMngVO.getFlag().equals("u")){
            // 유저 수정
            Map<String,Object> rtnMap = userMngService.getUserDetail(userMngVO);

            if((boolean)rtnMap.get("result")){
                model.addAttribute("userMngVO",rtnMap.get("value"));
            }else{
                redirectAttributes.addFlashAttribute("rMsg",rtnMap.get("rMsg"));
                return "redirect:/mng/user/list";
            }
        }else{
            model.addAttribute("userMngVO", new UserMngVO());
        }

        return "mng/user/form";
    }

    /**
     * 유저  생성/수정/삭제
     * @param userMngVO
     * @param request
     * @param response
     * @param model
     * @param session
     * @param redirectAttributes
     * @param principal
     * @return
     */
    @RequestMapping("/mng/user/proc")
    public String userProc(@ModelAttribute("userMngVO") UserMngVO userMngVO,
                           HttpServletRequest request,
                           HttpServletResponse response,
                           Model model,
                           HttpSession session,
                           RedirectAttributes redirectAttributes,
                           Principal principal) throws Exception {


        Map<String,Object> rtnMap = userMngService.userInfoProc(userMngVO);
        redirectAttributes.addFlashAttribute("rMsg",rtnMap.get("rMsg"));

        if((boolean)rtnMap.get("result")){
            return "redirect:" + rtnMap.get("redirectUrl");
        }else{
            return "redirect:/mng/user/list";
        }
    }
}
