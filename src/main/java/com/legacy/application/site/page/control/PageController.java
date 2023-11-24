package com.legacy.application.site.page.control;

import com.legacy.application.common.util.CommonUtil;
import com.legacy.application.site.page.service.PageService;
import com.legacy.application.site.page.vo.PageVO;
import org.springframework.stereotype.Controller;
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

@Controller
public class PageController {

    @Resource(name = "pageService")
    PageService pageService;

    /**
     * HTML 정보 상세보기
     * @param pageVO
     * @param request
     * @param response
     * @param model
     * @param session
     * @param redirectAttributes
     * @param principal
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/page/{html_seq}")
    public String pageDetail(@ModelAttribute("pageVO") PageVO pageVO,
                             HttpServletRequest request,
                             HttpServletResponse response,
                             ModelMap model,
                             HttpSession session,
                             RedirectAttributes redirectAttributes,
                             Principal principal) throws Exception {

        if(CommonUtil.isNumber(pageVO.getHtml_seq()) == false) {
            redirectAttributes.addFlashAttribute("rMsg","올바르지 않은 접근입니다.");
            return "redirect:/main";
        } else {
            PageVO info = pageService.getPageDetail(pageVO);
            if(info == null){
                redirectAttributes.addFlashAttribute("rMsg","존재하지 않는 페이지입니다.");
                return "redirect:/main";
            }

            model.addAttribute("pageVO", info);

            return "site/page/detail";
        }
    }
}
