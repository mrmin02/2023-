package com.legacy.application.mng.html.mapper;

import com.legacy.application.mng.html.vo.HtmlMngVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("htmlMngMapper")
public interface HtmlMngMapper {

    /**
     * html 리스트
     * @param htmlMngVO
     * @return
     * @throws Exception
     */
    List<HtmlMngVO> getHtmlList(HtmlMngVO htmlMngVO) throws Exception;

    /**
     * html 상세정보
     * @param htmlMngVO
     * @return
     * @throws Exception
     */
    HtmlMngVO getHtml(HtmlMngVO htmlMngVO) throws Exception;

    /**
     * html 등록
     * @param htmlMngVO
     * @return
     * @throws Exception
     */
    int setHtml(HtmlMngVO htmlMngVO) throws Exception;

    /**
     * html 수정
     * @param htmlMngVO
     * @return
     * @throws Exception
     */
    int updHtml(HtmlMngVO htmlMngVO) throws Exception;

    /**
     * html 삭제
     * @param htmlMngVO
     * @return
     * @throws Exception
     */
    int delHtml(HtmlMngVO htmlMngVO) throws Exception;
}
