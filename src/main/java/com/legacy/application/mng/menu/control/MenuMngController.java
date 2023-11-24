package com.legacy.application.mng.menu.control;

import com.legacy.application.mng.menu.service.MenuMngService;
import com.legacy.application.mng.menu.vo.MenuMngVO;
import com.legacy.application.site.page.vo.PageVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class MenuMngController {

    @Resource(name = "menuMngService")
    MenuMngService menuMngService;

    @RequestMapping(value = "/mng/menu/form")
    public String menuForm(@ModelAttribute("menuMngVO") MenuMngVO menuMngVO,
                             HttpServletRequest request,
                             HttpServletResponse response,
                             ModelMap model,
                             HttpSession session,
                             RedirectAttributes redirectAttributes,
                             Principal principal) throws Exception {

        model.addAttribute("json",menuMngService.getNowJson());

        return "mng/menu/form";
    }

    @ResponseBody
    @RequestMapping(value = "/mng/ajax/menu/{menu_type}/list")
    public List<MenuMngVO> menuTypeList(@ModelAttribute("menuMngVO") MenuMngVO menuMngVO,
                                    HttpServletRequest request,
                                    HttpServletResponse response,
                                    ModelMap model,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes,
                                    Principal principal) throws Exception {

        return menuMngService.getMenuTypeList(menuMngVO);
    }

    @RequestMapping(value = "/mng/menu/proc")
    public String menuProc(@ModelAttribute("menuMngVO") MenuMngVO menuMngVO,
                                        HttpServletRequest request,
                                        HttpServletResponse response,
                                        ModelMap model,
                                        HttpSession session,
                                        RedirectAttributes redirectAttributes,
                                        Principal principal) throws Exception {

        Map<String,Object> rtnMap = menuMngService.menuProc(menuMngVO);

        return "redirect:/mng/menu/form";
    }
}
