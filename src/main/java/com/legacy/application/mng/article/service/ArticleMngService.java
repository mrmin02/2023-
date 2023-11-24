package com.legacy.application.mng.article.service;

import com.legacy.application.common.system.file.service.FileMngService;
import com.legacy.application.common.system.file.vo.FileMngVO;
import com.legacy.application.common.system.login.vo.LoginVO;
import com.legacy.application.common.util.CommonUtil;
import com.legacy.application.common.util.FileUtil;
import com.legacy.application.common.util.GlobalsProperties;
import com.legacy.application.mng.article.mapper.ArticleMngMapper;
import com.legacy.application.mng.article.vo.ArticleMngVO;
import com.legacy.application.mng.bbs.mapper.BbsMngMapper;
import com.legacy.application.mng.bbs.vo.BbsMngVO;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("articleMngService")
public class ArticleMngService {

    @Resource(name = "articleMngMapper")
    ArticleMngMapper articleMngMapper;

    @Resource(name = "bbsMngMapper")
    BbsMngMapper bbsMngMapper;

    @Resource(name = "fileMngService")
    FileMngService fileMngService;


    public final String FILE_PATH = GlobalsProperties.getProperty("file.DefaultPath");

    public final String ARTICLE_PATH = GlobalsProperties.getProperty("file.ArticleFilePath");

    public List<ArticleMngVO> getArticleList(ArticleMngVO articleMngVO) throws Exception{
        return articleMngMapper.getArticleList(articleMngVO);
    }

    public Map<String,Object> getArticleDetail(ArticleMngVO articleMngVO) throws Exception{
        Map<String,Object> rtnMap = new HashMap<>();
        boolean result = false;
        String rMsg = "";

        try {

            BbsMngVO bbsMngVO = new BbsMngVO();
            bbsMngVO.setBbs_cd(articleMngVO.getBbs_cd());

            BbsMngVO bbsInfo = bbsMngMapper.getBbsDetail(bbsMngVO);

            rtnMap.put("bbsInfo",bbsInfo);

            if(bbsInfo != null){
                if(!StringUtils.isEmpty(articleMngVO.getArticle_seq())){
                    articleMngVO = articleMngMapper.getArticleDetail(articleMngVO);

                    if(articleMngVO != null){
                        result = true;
                        if(bbsInfo.getAttach_file_use_yn().equals("Y")){
                            rtnMap.put("files",fileMngService.getFiles(new FileMngVO("TB_ARTICLES",articleMngVO.getArticle_seq())));
                        }


                        rtnMap.put("value",articleMngVO);
                    }else{
                        result = false;
                        rMsg = "게시글 정보가 없습니다.";
                    }
                }else{
                    result = true;
                    rtnMap.put("value",articleMngVO);
                }
            }else{
                rMsg = "게시판 정보가 없습니다.";
            }

        }catch (Exception e){
            e.printStackTrace();
            rMsg = "게시판 정보 검색 중 오류가 발생했습니다.";
        }

        rtnMap.put("rMsg",rMsg);
        rtnMap.put("result",result);

        return rtnMap;
    }


    /**
     * 게시글 추가/수정/삭제
     * @param articleMngVO
     * @return
     * @throws Exception
     */
    public Map<String,Object> articleProc(ArticleMngVO articleMngVO, Principal principal) throws Exception{
        Map<String,Object> rtnMap = new HashMap<>();
        boolean result = false;
        String rMsg = "";
        String redirectUrl = "";
        LoginVO loginVO = (LoginVO) ((Authentication)principal).getDetails();

        BbsMngVO bbsMngVO = new BbsMngVO();
        bbsMngVO.setBbs_cd(articleMngVO.getBbs_cd());

        BbsMngVO info = bbsMngMapper.getBbsDetail(bbsMngVO);
        CommonUtil.fn_copyClass(articleMngVO, info);

        try{
            if(articleMngVO.getFlag() != null){
                switch (articleMngVO.getFlag()){
                    case "c": // 생성
                        articleMngVO.setUser_id(loginVO.getUser_id());
                        articleMngVO.setInpt_user_name(loginVO.getUser_name());
                        result = articleMngMapper.setArticle(articleMngVO) > 0; //게시판 정보 입력
                        if(result){
                            // TODO 첨부파일 정보 입력
                            boolean fileUploadResult = false;
                            if(articleMngVO.getAttach_file_use_yn().equals("Y")){
                                List<FileMngVO> fileList = FileUtil.fn_getFileInfo(articleMngVO,"TB_ARTICLES",articleMngVO.getArticle_seq(),FILE_PATH,ARTICLE_PATH,"",loginVO.getUser_seq());
                                if(fileList.size() > 0){
                                    for (FileMngVO item : fileList) {
                                        fileUploadResult = item.isFileResult();
                                        if(fileUploadResult){
                                            result = fileMngService.setFile(item) > 0;

                                            if(!result){
                                                break;
                                            }
                                        }else{
                                            break;
                                        }
                                    }
                                }else{
                                    fileUploadResult = true;
                                }

                                if(!fileUploadResult){
                                    // 파일 업로드 실패시, 게시글 물리삭제
                                    articleMngMapper.delArticleReal(articleMngVO);
                                    articleMngVO.setArticle_seq("");
                                    rMsg = "게시글 등록에 실패했습니다.";
                                    result = false;
                                    break;
                                }
                            }

                            if(result){
                                rMsg = "게시글 작성이 완료되었습니다.";
                                redirectUrl = "/mng/article/"+articleMngVO.getBbs_cd()+"/detail/"+articleMngVO.getArticle_seq();
                            }else{
                                rMsg = "게시글 작성에 실패하였습니다.";
                            }
                        }else{
                            rMsg = "게시글 작성에 실패하였습니다.";
                        }

                        break;
                    case "u": // 수정
                        result = articleMngMapper.updArticle(articleMngVO) > 0; // 게시판 정보 수정
                        if(result){
                            // TODO 첨부파일 수정 로직
                            boolean fileUploadResult = false;
                            if(articleMngVO.getAttach_file_use_yn().equals("Y")){
                                List<FileMngVO> fileList = FileUtil.fn_getFileInfo(articleMngVO,"TB_ARTICLES",articleMngVO.getArticle_seq(),FILE_PATH,ARTICLE_PATH,"",loginVO.getUser_seq());
                                if(fileList.size() > 0){
                                    for (FileMngVO item : fileList) {
                                        fileUploadResult = item.isFileResult();
                                        if(fileUploadResult){
                                            result = fileMngService.setFile(item) > 0;

                                            if(!result){
                                                break;
                                            }
                                        }else{
                                            break;
                                        }
                                    }
                                }else{
                                    fileUploadResult = true;
                                }
                            }
                            if(!fileUploadResult){
                                // 파일 업로드 실패시, 게시글 물리삭제
                                rMsg = "첨부파일 등록에 실패했습니다.";
                                result = false;
                                break;
                            }
                            // 삭제할 첨부파일 삭제
                            if(articleMngVO.getDel_file_seq()!= null && articleMngVO.getDel_file_seq().length > 0){
                                for (String del_seq: articleMngVO.getDel_file_seq()) {
                                    if(!fileUploadResult){
                                        break;
                                    }
                                    if(CommonUtil.isNotEmpty(del_seq)){

                                        FileMngVO checkVO = fileMngService.getFileDetail(new FileMngVO(del_seq));
                                        if(checkVO.getTable_seq().equals(articleMngVO.getArticle_seq())){
                                            FileMngVO delFile = new FileMngVO(del_seq);
                                            delFile.setUpd_seq(loginVO.getUser_seq());
                                            fileMngService.delFile(delFile);
                                        }else{
                                            fileUploadResult = false;
                                            break;
                                        }
                                    }
                                }
                            }
                            if(!fileUploadResult){
                                // 파일 업로드 실패시, 게시글 물리삭제
                                rMsg = "첨부파일 삭제 중 오류가 발생했습니다.";
                                result = false;
                                break;
                            }

                            if(result){
                                rMsg = "게시글 수정이 완료되었습니다.";
                                redirectUrl = "/mng/article/"+articleMngVO.getBbs_cd()+"/detail/"+articleMngVO.getArticle_seq();
                            }else{
                                rMsg = "게시글 수정에 실패하였습니다. 다시 시도해주세요.";
                            }
                        }else{
                            rMsg = "게시글 수정에 실패하였습니다.";
                        }

                        break;
                    case "d": // 삭제
                        result = articleMngMapper.delArticle(articleMngVO) > 0; // 게시판 정보 삭제 ( flag )
                        if(result){
                            rMsg = "게시글 삭제가 완료되었습니다.";
                            redirectUrl = "/mng/article/"+articleMngVO.getBbs_cd()+"/list";
                        }else{
                            rMsg = "게시글 삭제에 실패하였습니다.";
                        }
                        break;
                    default:
                        rMsg = "잘못된 요청입니다. (code:articleMngInfoFlag01)";
                        break;
                }
            }else{
                rMsg = "잘못된 요청입니다. (code:articleMngInfoFlag00)";
            }
        }catch (Exception e){
            e.printStackTrace();
            result = false;
            rMsg = "게시글 정보 처리중 오류가 발생했습니다.";
        }



        rtnMap.put("result",result);
        rtnMap.put("rMsg", rMsg);
        rtnMap.put("redirectUrl", redirectUrl);

        return rtnMap;
    }
}
