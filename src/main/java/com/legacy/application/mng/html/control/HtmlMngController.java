package com.legacy.application.mng.html.control;

import com.legacy.application.common.util.CommonUtil;
import com.legacy.application.mng.article.vo.ArticleMngVO;
import com.legacy.application.mng.bbs.vo.BbsMngVO;
import com.legacy.application.mng.html.service.HtmlMngService;
import com.legacy.application.mng.html.vo.HtmlMngVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

@Controller
public class HtmlMngController {

    @Resource(name = "htmlMngService")
    HtmlMngService htmlMngService;

    /**
     * html 리스트
     * @param htmlMngVO
     * @param request
     * @param response
     * @param model
     * @param session
     * @param redirectAttributes
     * @param principal
     * @return
     * @throws Exception
     */
    @RequestMapping("/mng/html/list")
    public String htmlList(@ModelAttribute("htmlMngVO") HtmlMngVO htmlMngVO,
                              HttpServletRequest request,
                              HttpServletResponse response,
                              Model model,
                              HttpSession session,
                              RedirectAttributes redirectAttributes,
                              Principal principal) throws Exception {

        model.addAttribute("htmlMngVO",htmlMngVO);
        model.addAttribute("list",htmlMngService.getHtmlList(htmlMngVO));

        return "mng/html/list";
    }

    /**
     * html 상세정보
     * @param htmlMngVO
     * @param request
     * @param response
     * @param model
     * @param session
     * @param redirectAttributes
     * @param principal
     * @return
     * @throws Exception
     */
    @RequestMapping("/mng/html/detail/{html_seq}")
    public String htmlDetail(@ModelAttribute("htmlMngVO") HtmlMngVO htmlMngVO,
                              HttpServletRequest request,
                              HttpServletResponse response,
                              Model model,
                              HttpSession session,
                              RedirectAttributes redirectAttributes,
                              Principal principal) throws Exception {

        Map<String, Object> rtnMap = htmlMngService.getHtml(htmlMngVO);

        if((boolean)rtnMap.get("result")){
            model.addAttribute("htmlMngVO", rtnMap.get("html"));
        }else{
            redirectAttributes.addFlashAttribute("rMsg",rtnMap.get("rMsg"));
            return "redirect:/mng/html/list";
        }

        return "mng/html/detail";
    }

    /**
     * html form
     * @param htmlMngVO
     * @param request
     * @param response
     * @param model
     * @param session
     * @param redirectAttributes
     * @param principal
     * @return
     * @throws Exception
     */
    @RequestMapping("/mng/html/form")
    public String htmlForm(@ModelAttribute("htmlMngVO") HtmlMngVO htmlMngVO,
                             HttpServletRequest request,
                             HttpServletResponse response,
                             Model model,
                             HttpSession session,
                             RedirectAttributes redirectAttributes,
                             Principal principal) throws Exception {

        Map<String, Object> rtnMap = htmlMngService.getHtml(htmlMngVO);

        if((boolean)rtnMap.get("result")){
            model.addAttribute("htmlMngVO", rtnMap.get("html"));
        }else{
            redirectAttributes.addFlashAttribute("rMsg",rtnMap.get("rMsg"));
            return "redirect:/mng/html/list";
        }

        return "mng/html/form";
    }

    /**
     * html form
     * @param htmlMngVO
     * @param request
     * @param response
     * @param model
     * @param session
     * @param redirectAttributes
     * @param principal
     * @return
     * @throws Exception
     */
    @RequestMapping("/mng/html/proc")
    public String htmlProc(@ModelAttribute("htmlMngVO") HtmlMngVO htmlMngVO,
                           HttpServletRequest request,
                           HttpServletResponse response,
                           Model model,
                           HttpSession session,
                           RedirectAttributes redirectAttributes,
                           Principal principal) throws Exception {

        Map<String,Object> rtnMap = htmlMngService.htmlProc(htmlMngVO);
        redirectAttributes.addFlashAttribute("rMsg",rtnMap.get("rMsg"));

        if((boolean)rtnMap.get("result")){
            return "redirect:" + rtnMap.get("redirectUrl");
        }else{
            return "redirect:/mng/html/list";
        }
    }
}
