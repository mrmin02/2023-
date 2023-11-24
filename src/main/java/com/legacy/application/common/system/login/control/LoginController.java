package com.legacy.application.common.system.login.control;

import com.legacy.application.common.system.login.service.LoginService;
import com.legacy.application.common.system.login.vo.LoginVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * 로그인 컨트롤러
 * @author mrmin
 *
 */
@Controller
public class LoginController {

    @Resource(name = "loginService")
    LoginService loginService;

    /**
     * 사용자 로그인 페이지
     * @param loginVO
     * @param request
     * @param response
     * @param model
     * @param session
     * @param redirectAttributes
     * @param principal
     * @return
     */
    @RequestMapping("/login")
    public String login(@ModelAttribute("loginVO") LoginVO loginVO,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        ModelMap model,
                        HttpSession session,
                        RedirectAttributes redirectAttributes,
                        Principal principal) throws Exception{
        if(principal != null){
            redirectAttributes.addFlashAttribute("rHeader","알림!");
            redirectAttributes.addFlashAttribute("rMsg","이미 로그인 되어있습니다.");
            return "redirect:/main";
        }

        return "site/login";
    }

    @RequestMapping("/mng/login")
    public String mngLogin(@ModelAttribute("loginVO") LoginVO loginVO,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        ModelMap model,
                        HttpSession session,
                        RedirectAttributes redirectAttributes,
                        Principal principal) throws Exception {
        if(principal != null){
            redirectAttributes.addFlashAttribute("rHeader","알림!");
            redirectAttributes.addFlashAttribute("rMsg","이미 로그인 되어있습니다.");
            return "redirect:/main";
        }

        return "/mng/login";
    }

}
