package com.legacy.application.site.article.service;

import com.legacy.application.common.system.file.mapper.FileMngMapper;
import com.legacy.application.common.system.file.service.FileMngService;
import com.legacy.application.common.system.file.vo.FileMngVO;
import com.legacy.application.common.system.login.vo.LoginVO;
import com.legacy.application.common.util.CommonUtil;
import com.legacy.application.common.util.FileUtil;
import com.legacy.application.common.util.GlobalsProperties;
import com.legacy.application.mng.article.vo.ArticleMngVO;
import com.legacy.application.mng.bbs.mapper.BbsMngMapper;
import com.legacy.application.mng.bbs.vo.BbsMngVO;
import com.legacy.application.site.article.mapper.ArticleMapper;
import com.legacy.application.site.article.vo.ArticleVO;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("articleService")
public class ArticleService {

    @Resource(name = "articleMapper")
    ArticleMapper articleMapper;

    @Resource(name = "bbsMngMapper")
    BbsMngMapper bbsMngMapper;

    @Resource(name = "fileMngService")
    FileMngService fileMngService;

    public final String FILE_PATH = GlobalsProperties.getProperty("file.DefaultPath");

    public final String ARTICLE_PATH = GlobalsProperties.getProperty("file.ArticleFilePath");

    public List<ArticleVO> getMainArticleList(ArticleVO articleVO) throws Exception{
        return articleMapper.getMainArticleList(articleVO);
    }

    public int getArticleListCnt(ArticleVO articleVO) throws Exception{
        return articleMapper.getArticleListCnt(articleVO);
    }
    public List<ArticleVO> getNoticeList(ArticleVO articleVO) throws Exception{
        return articleMapper.getNoticeList(articleVO);
    }
    public List<ArticleVO> getArticleList(ArticleVO articleVO) throws Exception{
        return articleMapper.getArticleList(articleVO);
    }

    public Map<String,Object> getArticleDetail(ArticleVO articleVO, Principal principal) throws Exception{
        Map<String,Object> rtnMap = new HashMap<>();
        boolean result = false;
        String rMsg = "";
        String flag = articleVO.getFlag();
        try {

            BbsMngVO bbsMngVO = new BbsMngVO();
            bbsMngVO.setBbs_cd(articleVO.getBbs_cd());

            BbsMngVO bbsinfo = bbsMngMapper.getBbsDetail(bbsMngVO);

            rtnMap.put("bbsInfo", bbsinfo);

            if(bbsinfo != null){
                if(!StringUtils.isEmpty(articleVO.getArticle_seq())){
                    ArticleVO tmpVO = new ArticleVO();
                    tmpVO.setSearchCondition(articleVO.getSearchCondition());
                    tmpVO.setSearchKeyword(articleVO.getSearchKeyword());

                    articleVO = articleMapper.getArticleDetail(articleVO);

                    if(articleVO != null){

                        articleVO.setSearchCondition(tmpVO.getSearchCondition());
                        articleVO.setSearchKeyword(tmpVO.getSearchKeyword());

                        result = true;

                        if(CommonUtil.isNotEmpty(flag) && flag.equals("r")){
                            // 조회수 증가
                            articleMapper.updArticleReadCNT(articleVO);
                        }

                        // 읽기/수정/삭제 권한
                        rtnMap.put("user_auth", getAuth(articleVO,principal));

                        if(bbsinfo.getAttach_file_use_yn().equals("Y")){
                            rtnMap.put("files",fileMngService.getFiles(new FileMngVO("TB_ARTICLES",articleVO.getArticle_seq())));
                        }

                        rtnMap.put("value",articleVO);

                    }else{
                        result = false;
                        rMsg = "게시글 정보가 없습니다.";
                    }
                }else{
                    /**
                     * 게시글 신규 작성
                     */
                    // 읽기/수정/삭제 권한
                    rtnMap.put("user_auth", getAuth(articleVO,principal));

                    result = true;
                    rtnMap.put("value",articleVO);
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
     * @param articleVO
     * @return
     * @throws Exception
     */
    public Map<String,Object> articleProc(ArticleVO articleVO, Principal principal) throws Exception{
        Map<String,Object> rtnMap = new HashMap<>();
        boolean result = false;
        String rMsg = "";
        String redirectUrl = "";
        LoginVO loginVO = (LoginVO) ((Authentication)principal).getDetails();

        BbsMngVO bbsMngVO = new BbsMngVO();
        bbsMngVO.setBbs_cd(articleVO.getBbs_cd());

        BbsMngVO info = bbsMngMapper.getBbsDetail(bbsMngVO);
        CommonUtil.fn_copyClass(articleVO, info);


        try{
            if(articleVO.getFlag() != null){
                switch (articleVO.getFlag()){
                    case "c": // 생성
                        articleVO.setUser_id(loginVO.getUser_id());
                        articleVO.setInpt_user_name(loginVO.getUser_name());
                        result = articleMapper.setArticle(articleVO) > 0; //게시판 정보 입력
                        if(result){
                            // TODO 첨부파일 정보 입력
                            boolean fileUploadResult = false;
                            if(articleVO.getAttach_file_use_yn().equals("Y")){
                                List<FileMngVO> fileList = FileUtil.fn_getFileInfo(articleVO,"TB_ARTICLES",articleVO.getArticle_seq(),FILE_PATH,ARTICLE_PATH,"",loginVO.getUser_seq());
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
                                    articleMapper.delArticleReal(articleVO);
                                    articleVO.setArticle_seq("");
                                    rMsg = "게시글 등록에 실패했습니다.";
                                    result = false;
                                    break;
                                }
                            }

                            if(result){
                                rMsg = "게시글 작성이 완료되었습니다.";
                                redirectUrl = "/article/"+articleVO.getBbs_cd()+"/detail/"+articleVO.getArticle_seq();
                            }else{
                                rMsg = "게시글 작성에 실패하였습니다.";
                            }
                        }else{
                            rMsg = "게시글 작성에 실패하였습니다.";
                        }

                        break;
                    case "u": // 수정
                        result = articleMapper.updArticle(articleVO) > 0; // 게시판 정보 수정
                        if(result){
                            // TODO 첨부파일 수정 로직
                            boolean fileUploadResult = false;
                            if(articleVO.getAttach_file_use_yn().equals("Y")){
                                List<FileMngVO> fileList = FileUtil.fn_getFileInfo(articleVO,"TB_ARTICLES",articleVO.getArticle_seq(),FILE_PATH,ARTICLE_PATH,"",loginVO.getUser_seq());
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
                            if(articleVO.getDel_file_seq()!= null && articleVO.getDel_file_seq().length > 0){
                                for (String del_seq: articleVO.getDel_file_seq()) {
                                    if(!fileUploadResult){
                                        break;
                                    }
                                    if(CommonUtil.isNotEmpty(del_seq)){

                                        FileMngVO checkVO = fileMngService.getFileDetail(new FileMngVO(del_seq));
                                        if(checkVO.getTable_seq().equals(articleVO.getArticle_seq())){
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
                                redirectUrl = "/article/"+articleVO.getBbs_cd()+"/detail/"+articleVO.getArticle_seq();
                            }else{
                                rMsg = "게시글 수정에 실패하였습니다. 다시 시도해주세요.";
                            }
                        }else{
                            rMsg = "게시글 수정에 실패하였습니다.";
                        }

                        break;
                    case "d": // 삭제
                        result = articleMapper.delArticle(articleVO) > 0; // 게시판 정보 삭제 ( flag )
                        if(result){
                            rMsg = "게시글 삭제가 완료되었습니다.";
                            redirectUrl = "/article/"+articleVO.getBbs_cd()+"/list";
                        }else{
                            rMsg = "게시글 삭제에 실패하였습니다.";
                        }
                        break;
                    default:
                        rMsg = "잘못된 요청입니다. (code:articleInfoFlag01)";
                        break;
                }
            }else{
                rMsg = "잘못된 요청입니다. (code:articleInfoFlag00)";
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

    /**
     * READ/UPDATE/DELETE 권한 체크 FIXME 역할별 권한관리 기능 구현하면 해당 함수 수정필요
     * 읽기 권한은 모든 사람에게 부여...
     * @param vo
     * @param principal
     * @return
     * @throws Exception
     */
    public Map<String, Boolean> getAuth(ArticleVO vo, Principal principal) throws Exception{
        Map<String,Boolean> authMap = new HashMap<>();

        boolean auth_read = false;
        boolean auth_create = false;
        boolean auth_update = false;
        boolean auth_delete = false;

        if(principal == null){
            /**
             * 비회원 ( 읽기 권한만 부여 )
             */
            auth_read = true;
        }else{
            LoginVO loginVO = (LoginVO) ((Authentication)principal).getDetails();

            if(loginVO.getUser_auth().equals("ROLE_ADMIN")){
                /**
                 * 관리자일 때
                 */
                auth_read = true;
                auth_create = true;
                auth_update = true;
                auth_delete = true;
            }else{
                /**
                 * 회원일 때
                 */
                auth_read = true;
                auth_create = true;
                if(CommonUtil.isNotEmpty(vo.getUser_id())){
                    if(vo.getUser_id().equals(loginVO.getUser_id())){
                        /**
                         * 자신이 쓴 글일 때만 수정 삭제 권한 부여
                         */
                        auth_update = true;
                        auth_delete = true;
                    }
                }
            }
        }

        authMap.put("auth_read",auth_read);
        authMap.put("auth_create",auth_create);
        authMap.put("auth_update",auth_update);
        authMap.put("auth_delete",auth_delete);

        return authMap;
    }

    /**
     * 플래그 값과 권한 교차 검증
     * @param flag
     * @param auth_map
     * @return
     */
    public boolean checkFlagAndAuth(String flag, Map<String,Boolean> auth_map) throws Exception{

        boolean result = false;

        if(StringUtils.isEmpty(flag)){
            result = false;
        }else{
            switch (flag){
                case "c":
                    if(auth_map.get("auth_create")){
                        result = true;
                    }
                    break;

                case "u":
                    if(auth_map.get("auth_update")){
                        result = true;
                    }
                    break;
                case "d":
                    if(auth_map.get("auth_delete")){
                        result = true;
                    }
                    break;
                default:
                    result = false;
                    break;
            }
        }

        return result;
    }


}
