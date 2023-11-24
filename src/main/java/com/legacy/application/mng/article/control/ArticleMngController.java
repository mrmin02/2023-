package com.legacy.application.mng.article.control;

import com.legacy.application.common.util.CommonUtil;
import com.legacy.application.mng.article.service.ArticleMngService;
import com.legacy.application.mng.article.vo.ArticleMngVO;
import com.legacy.application.mng.bbs.service.BbsMngService;
import com.legacy.application.mng.bbs.vo.BbsMngVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
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
public class ArticleMngController {

    @Resource(name = "articleMngService")
    ArticleMngService articleMngService;

    @Resource(name = "bbsMngService")
    BbsMngService bbsMngService;


    /**
     * 게시글 리스트
     * @param articleMngVO
     * @param request
     * @param response
     * @param model
     * @param session
     * @param redirectAttributes
     * @param principal
     * @return
     * @throws Exception
     */
    @RequestMapping("/mng/article/{bbs_cd}/list")
    public String articleList(@ModelAttribute("articleMngVO") ArticleMngVO articleMngVO,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          Model model,
                          HttpSession session,
                          RedirectAttributes redirectAttributes,
                          Principal principal) throws Exception {
        BbsMngVO bbsMngVO = new BbsMngVO();
        bbsMngVO.setBbs_cd(articleMngVO.getBbs_cd());
        Map<String,Object> bbsMap = bbsMngService.getBbsDetail(bbsMngVO);
        if((boolean)bbsMap.get("result")){
            CommonUtil.fn_copyClass(articleMngVO, (BbsMngVO) bbsMap.get("value"));
        }else{
            redirectAttributes.addFlashAttribute("rMsg",bbsMap.get("rMsg"));
            return "redirect:/mng/bbs/list";
        }

        model.addAttribute("articleMngVO",articleMngVO);
        model.addAttribute("list",articleMngService.getArticleList(articleMngVO));

        return "mng/article/list";
    }

    /**
     * 게시글 상세정보
     * @param articleMngVO
     * @param request
     * @param response
     * @param model
     * @param session
     * @param redirectAttributes
     * @param principal
     * @return
     */
    @RequestMapping("/mng/article/{bbs_cd}/detail/{article_seq}")
    public String articleDetail(@ModelAttribute("articleMngVO") ArticleMngVO articleMngVO,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          Model model,
                          HttpSession session,
                          RedirectAttributes redirectAttributes,
                          Principal principal) throws Exception {

        Map<String,Object> rtnMap = articleMngService.getArticleDetail(articleMngVO);

        if((boolean)rtnMap.get("result")){
            model.addAttribute("articleMngVO",rtnMap.get("value"));
            model.addAttribute("bbsInfo",rtnMap.get("bbsInfo"));
            if(rtnMap.get("files") != null){
                model.addAttribute("files",rtnMap.get("files"));
            }
        }else{
            redirectAttributes.addFlashAttribute("rMsg",rtnMap.get("rMsg"));
            return "redirect:/mng/article/"+articleMngVO.getBbs_cd()+"/list";
        }

        return "mng/article/detail";
    }

    /**
     * 게시글 form
     * @param articleMngVO
     * @param request
     * @param response
     * @param model
     * @param session
     * @param redirectAttributes
     * @param principal
     * @return
     */
    @RequestMapping("/mng/article/{bbs_cd}/form")
    public String articleForm(@ModelAttribute("articleMngVO") ArticleMngVO articleMngVO,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          Model model,
                          HttpSession session,
                          RedirectAttributes redirectAttributes,
                          Principal principal) throws Exception {

        // 게시판 정보
        Map<String,Object> rtnMap = articleMngService.getArticleDetail(articleMngVO);

        if((boolean)rtnMap.get("result")){
            model.addAttribute("bbsInfo",rtnMap.get("bbsInfo"));
            model.addAttribute("articleMngVO",rtnMap.get("value"));
            if(rtnMap.get("files") != null){
                model.addAttribute("files",rtnMap.get("files"));
            }
        }else{
            redirectAttributes.addFlashAttribute("rMsg",rtnMap.get("rMsg"));
            return "redirect:/mng/article/"+articleMngVO.getBbs_cd()+"/list";
        }

        return "mng/article/form";
    }

    /**
     * 게시글 정보 생성/수정/삭제
     * @param articleMngVO
     * @param request
     * @param response
     * @param model
     * @param session
     * @param redirectAttributes
     * @param principal
     * @return
     */
    @RequestMapping("/mng/article/{bbs_cd}/proc")
    public String bbsProc(@ModelAttribute("articleMngVO") ArticleMngVO articleMngVO,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          Model model,
                          HttpSession session,
                          RedirectAttributes redirectAttributes,
                          Principal principal) throws Exception {

        articleMngVO.setInpt_ip(CommonUtil.getIP(request));
        articleMngVO.setUpd_ip(CommonUtil.getIP(request));

        Map<String,Object> rtnMap = articleMngService.articleProc(articleMngVO, principal);
        redirectAttributes.addFlashAttribute("rMsg",rtnMap.get("rMsg"));

        if((boolean)rtnMap.get("result")){
            return "redirect:" + rtnMap.get("redirectUrl");
        }else{
            return "redirect:/mng/article/"+articleMngVO.getBbs_cd()+"/list";
        }
    }
}
