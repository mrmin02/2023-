package com.legacy.application.mng.article.mapper;

import com.legacy.application.mng.article.vo.ArticleMngVO;
import com.legacy.application.site.article.vo.ArticleVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;


import java.util.List;

@Mapper("articleMngMapper")
public interface ArticleMngMapper {

    /**
     * 게시글 리스트
     * @param articleMngVO
     * @return
     * @throws Exception
     */
    List<ArticleMngVO> getArticleList(ArticleMngVO articleMngVO) throws Exception;

    /**
     * 게시글 상세정보
     * @param articleMngVO
     * @return
     * @throws Exception
     */
    ArticleMngVO getArticleDetail(ArticleMngVO articleMngVO) throws Exception;

    /**
     * 게시글 insert
     * @param articleMngVO
     * @return
     * @throws Exception
     */
    int setArticle(ArticleMngVO articleMngVO) throws Exception;

    /**
     * 게시글 수정
     * @param articleMngVO
     * @return
     * @throws Exception
     */
    int updArticle(ArticleMngVO articleMngVO) throws Exception;

    /**
     * 게시글 삭제
     * @param articleMngVO
     * @return
     * @throws Exception
     */
    int delArticle(ArticleMngVO articleMngVO) throws Exception;

    /**
     * 게시글 물리삭제
     * @param articleMngVO
     * @return
     * @throws Exception
     */
    int delArticleReal(ArticleMngVO articleMngVO) throws Exception;

}
