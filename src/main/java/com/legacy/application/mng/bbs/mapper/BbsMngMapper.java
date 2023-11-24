package com.legacy.application.mng.bbs.mapper;

import com.legacy.application.mng.bbs.vo.BbsMngVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper("bbsMngMapper")
public interface BbsMngMapper {

    /**
     * 게시판 정보 리스트
     * @param bbsMngVO
     * @return
     * @throws Exception
     */
    List<BbsMngVO> getBbsList(BbsMngVO bbsMngVO) throws Exception;

    /**
     * 게시판 코드 중복 체크
     * @param bbsMngVO
     * @return
     * @throws Exception
     */
    int checkBbsCd(BbsMngVO bbsMngVO) throws Exception;

    /**
     * 게시판 detail get
     * @param bbsMngVO
     * @return
     * @throws Exception
     */
    BbsMngVO getBbsDetail(BbsMngVO bbsMngVO) throws Exception;

    /**
     * 게시판 정보 insert ( TB_BBS_INFO )
     * @param bbsMngVO
     * @return
     * @throws Exception
     */
    int setBbsInfo(BbsMngVO bbsMngVO) throws Exception;

    /**
     * 게시판 정보 update ( TB_BBS_INFO )
     * @param bbsMngVO
     * @return
     * @throws Exception
     */
    int updBbsInfo(BbsMngVO bbsMngVO) throws Exception;

    /**
     * 게시판 정보 delete ( TB_BBS_INFO )
     * @param bbsMngVO
     * @return
     * @throws Exception
     */
    int delBbsInfo(BbsMngVO bbsMngVO) throws Exception;

    /**
     * 게시판 정보 물리삭제
     */
    int delRealBbsInfo(BbsMngVO bbsMngVO) throws Exception;

    /**
     * 게시판 상세정보 등록
     * @param bbsMngVO
     * @return
     * @throws Exception
     */
    int setBbsDetail(BbsMngVO bbsMngVO) throws Exception;

    /**
     * 게시판 상세정보 수정
     * @param bbsMngVO
     * @return
     * @throws Exception
     */
    int updBbsDetail(BbsMngVO bbsMngVO) throws Exception;
}
