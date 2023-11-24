package com.legacy.application.site.main.control;


import java.security.Principal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.legacy.application.site.article.service.ArticleService;
import com.legacy.application.site.article.vo.ArticleVO;
import com.legacy.application.site.main.vo.MenuVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.legacy.application.site.main.service.MainService;
/**
 * 메인 컨트롤러
 * @author mrmin
 *
 */
@Controller
public class MainController {
	
	@Resource(name = "mainService")
	MainService mainService;

	@Resource(name = "articleService")
	ArticleService articleService;

	/**
	 * 메인페이지 redirect
	 * @param model
	 * @return
	 */
	@RequestMapping("/")
	public String mainRedirect( Model model
				, HttpServletRequest request
				, HttpServletResponse response
				, RedirectAttributes redirectAttributes
				, Principal principal) throws Exception{

		return "redirect:/main";
	}

	/**
	 * 메인페이지
	 * @param model
	 * @return
	 */
	@RequestMapping("/main")
	public String main( Model model
			, HttpServletRequest request
			, HttpServletResponse response
			, RedirectAttributes redirectAttributes
			, Principal principal) throws Exception{

		ArticleVO articleVO = new ArticleVO("NOTICE");

		// 메인페이지 공지사항 노출
		model.addAttribute("noticeList", articleService.getMainArticleList(articleVO));

		return "/site/mainPage";
	}

	/**
	 * 메뉴 정보
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/ajax/menu/list")
	public List<MenuVO> menuList(Model model
			, HttpServletRequest request
			, HttpServletResponse response
			, RedirectAttributes redirectAttributes
			, Principal principal) throws Exception{

		return mainService.getMenuList();
	}
}
