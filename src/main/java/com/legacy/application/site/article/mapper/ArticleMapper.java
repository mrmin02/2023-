package com.legacy.application.site.article.mapper;

import com.legacy.application.mng.article.vo.ArticleMngVO;
import com.legacy.application.site.article.vo.ArticleVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("articleMapper")
public interface ArticleMapper {

    public List<ArticleVO> getNoticeList(ArticleVO articleVO) throws Exception;

    public List<ArticleVO> getArticleList(ArticleVO articleVO) throws Exception;

    public int getArticleListCnt(ArticleVO articleVO) throws Exception;

    ArticleVO getBbsConfig(ArticleVO articleVO) throws Exception;

    /**
     * 메인페이지에서 사용할 게시물 가져오기
     * @param articleVO
     * @return
     * @throws Exception
     */
    List<ArticleVO> getMainArticleList(ArticleVO articleVO) throws Exception;

    ArticleVO getArticleDetail(ArticleVO articleVO) throws Exception;

    int updArticleReadCNT(ArticleVO articleVO) throws Exception;


    /**
     * 게시글 insert
     * @param articleVO
     * @return
     * @throws Exception
     */
    int setArticle(ArticleVO articleVO) throws Exception;

    /**
     * 게시글 수정
     * @param articleVO
     * @return
     * @throws Exception
     */
    int updArticle(ArticleVO articleVO) throws Exception;

    /**
     * 게시글 삭제
     * @param articleVO
     * @return
     * @throws Exception
     */
    int delArticle(ArticleVO articleVO) throws Exception;

    /**
     * 게시글 물리삭제
     * @param articleVO
     * @return
     * @throws Exception
     */
    int delArticleReal(ArticleVO articleVO) throws Exception;

}
