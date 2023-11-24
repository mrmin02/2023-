package com.legacy.application.site.article.control;

import com.legacy.application.common.system.file.service.FileMngService;
import com.legacy.application.common.util.CommonUtil;
import com.legacy.application.mng.article.vo.ArticleMngVO;
import com.legacy.application.mng.bbs.service.BbsMngService;
import com.legacy.application.mng.bbs.vo.BbsMngVO;
import com.legacy.application.site.article.service.ArticleService;
import com.legacy.application.site.article.vo.ArticleVO;
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
public class ArticleController {

    @Resource(name = "articleService")
    ArticleService articleService;

    @Resource(name = "bbsMngService")
    BbsMngService bbsMngService;

    @Resource(name = "fileMngService")
    FileMngService fileMngService;


    /**
     * 게시글 리스트
     * @param articleVO
     * @param request
     * @param response
     * @param model
     * @param session
     * @param redirectAttributes
     * @param principal
     * @return
     * @throws Exception
     */
    @RequestMapping("/article/{bbs_cd}/list")
    public String articleList(@ModelAttribute("articleVO") ArticleVO articleVO,
                              HttpServletRequest request,
                              HttpServletResponse response,
                              Model model,
                              HttpSession session,
                              RedirectAttributes redirectAttributes,
                              Principal principal) throws Exception {
        BbsMngVO bbsMngVO = new BbsMngVO();
        bbsMngVO.setBbs_cd(articleVO.getBbs_cd());
        Map<String,Object> bbsMap = bbsMngService.getBbsDetail(bbsMngVO);
        if((boolean)bbsMap.get("result")){
            bbsMngVO = (BbsMngVO) bbsMap.get("value");
            bbsMngVO.setPageIndex(articleVO.getPageIndex());
//            bbsMngVO.setSearchCondition(articleVO.getSearchCondition());
//            bbsMngVO.setSearchKeyword(articleVO.getSearchKeyword());
            CommonUtil.fn_copyClass(articleVO, bbsMngVO);
            if(bbsMngVO.getNotice_use_yn().equals("Y")){
                model.addAttribute("noticeList",articleService.getNoticeList(articleVO));
            }

            articleVO.setPageUnit(bbsMngVO.getBbs_page_cnt());
            articleVO.setRecordCountPerPage(bbsMngVO.getBbs_dft_list_cnt());
        }else{
            redirectAttributes.addFlashAttribute("rMsg",bbsMap.get("rMsg"));
            return "redirect:/main";
        }
        // 권한
        Map<String,Boolean>authMap = articleService.getAuth(articleVO,principal);

        if(!authMap.get("auth_read")){
            redirectAttributes.addFlashAttribute("권한이 없습니다.");
            return "redirect:/main";
        }

        model.addAttribute("user_auth",authMap);
        model.addAttribute("articleVO",articleVO);
        model.addAttribute("list",articleService.getArticleList(articleVO));
        model.addAttribute("totalCnt", articleService.getArticleListCnt(articleVO));

        return "site/article/list";
    }

    /**
     * 게시글 상세정보
     * @param articleVO
     * @param request
     * @param response
     * @param model
     * @param session
     * @param redirectAttributes
     * @param principal
     * @return
     */
    @RequestMapping("/article/{bbs_cd}/detail/{article_seq}")
    public String articleDetail(@ModelAttribute("articleVO") ArticleVO articleVO,
                                HttpServletRequest request,
                                HttpServletResponse response,
                                Model model,
                                HttpSession session,
                                RedirectAttributes redirectAttributes,
                                Principal principal) throws Exception {

        Map<String,Object> rtnMap = articleService.getArticleDetail(articleVO, principal);

        Map<String,Boolean> authMap = (Map<String,Boolean>)rtnMap.get("user_auth");

        if(!authMap.get("auth_read")){
            redirectAttributes.addFlashAttribute("권한이 없습니다.");
            return "redirect:/main";
        }

        model.addAttribute("bbsInfo",rtnMap.get("bbsInfo"));
        model.addAttribute("user_auth",authMap);

        if((boolean)rtnMap.get("result")){
            model.addAttribute("articleVO",rtnMap.get("value"));
            if(rtnMap.get("files") != null){
                model.addAttribute("files",rtnMap.get("files"));
            }
        }else{
            redirectAttributes.addFlashAttribute("rMsg",rtnMap.get("rMsg"));
            return "redirect:/article/"+articleVO.getBbs_cd()+"/list";
        }

        return "site/article/detail";
    }

    /**
     * 게시글 form
     * @param articleVO
     * @param request
     * @param response
     * @param model
     * @param session
     * @param redirectAttributes
     * @param principal
     * @return
     */
    @RequestMapping("/article/{bbs_cd}/form")
    public String articleForm(@ModelAttribute("articleVO") ArticleVO articleVO,
                              HttpServletRequest request,
                              HttpServletResponse response,
                              Model model,
                              HttpSession session,
                              RedirectAttributes redirectAttributes,
                              Principal principal) throws Exception {

        // 게시판 정보
        Map<String,Object> rtnMap = articleService.getArticleDetail(articleVO,principal);

        Map<String,Boolean>authMap = (Map<String,Boolean>)rtnMap.get("user_auth");

        boolean authResult = articleService.checkFlagAndAuth(articleVO.getFlag(), authMap);

        if(!authResult){
            redirectAttributes.addFlashAttribute("rMsg","권한이 없습니다.");
            return "redirect:/article/"+articleVO.getBbs_cd()+"/list";
        }

        model.addAttribute("bbsInfo",rtnMap.get("bbsInfo"));
        model.addAttribute("user_auth",authMap);


        if((boolean)rtnMap.get("result")){
            model.addAttribute("articleVO",rtnMap.get("value"));
            if(rtnMap.get("files") != null){
                model.addAttribute("files",rtnMap.get("files"));
            }
        }else{
            redirectAttributes.addFlashAttribute("rMsg",rtnMap.get("rMsg"));
            return "redirect:/article/"+articleVO.getBbs_cd()+"/list";
        }

        return "site/article/form";
    }


    /**
     * 게시글 정보 생성/수정/삭제
     * @param articleVO
     * @param request
     * @param response
     * @param model
     * @param session
     * @param redirectAttributes
     * @param principal
     * @return
     */
    @RequestMapping("/article/{bbs_cd}/proc")
    public String bbsProc(@ModelAttribute("articleVO") ArticleVO articleVO,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          Model model,
                          HttpSession session,
                          RedirectAttributes redirectAttributes,
                          Principal principal) throws Exception {

        ArticleVO userInput = articleVO;
        Map<String,Object> detailMap = articleService.getArticleDetail(articleVO, principal);

        Map<String,Boolean>authMap = (Map<String,Boolean>)detailMap.get("user_auth");
        boolean authResult = articleService.checkFlagAndAuth(articleVO.getFlag(), authMap);

        if(!authResult){
            redirectAttributes.addFlashAttribute("rMsg","권한이 없습니다.");
            return "redirect:/article/"+articleVO.getBbs_cd()+"/list";
        }

        articleVO.setInpt_ip(CommonUtil.getIP(request));
        articleVO.setUpd_ip(CommonUtil.getIP(request));

        Map<String,Object> rtnMap = articleService.articleProc(userInput, principal);
        redirectAttributes.addFlashAttribute("rMsg",rtnMap.get("rMsg"));

        if((boolean)rtnMap.get("result")){
            return "redirect:" + rtnMap.get("redirectUrl");
        }else{
            return "redirect:/article/"+articleVO.getBbs_cd()+"/list";
        }
    }
}