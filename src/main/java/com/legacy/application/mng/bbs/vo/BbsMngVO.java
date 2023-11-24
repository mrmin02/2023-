package com.legacy.application.mng.bbs.vo;

import lombok.Data;
import com.legacy.application.common.config.vo.DefaultVO;

/**
 * 게시판 정보 VO
 */
@Data
public class BbsMngVO extends DefaultVO{

    /**
     * 게시판 정보  TB_BBS_INFO
     */
    private String bbs_info_seq; // 게시판 정보 순번
    private String bbs_cd; // 게시판 코드
    private String bbs_nm; // 게시판 이름
    private String bbs_remark; // 게시판 설명

    private String bbs_use_yn; // 게시판 사용 여부
    private String bbs_del_yn; // 게시판 삭제 여부

    // TODO TB_BBS_DETAIL , TB_BBS_AUTH -> 권한별 관리는 보류
    private String attach_file_use_yn; // 첨부파일 사용여부
    private String file_cnt; // 첨부파일 개수
    private String bbs_type; // 게시판 타입 ( 리스트형, TODO 보류 -> qna )
    private int bbs_dft_list_cnt; // 게시판 목록 수
    private int bbs_page_cnt; // 게시판 페이지 수
    private String notice_use_yn; // 공지사항 사용 여부

    private String file_ext; // 파일 확장자 - \로 구분


    /**
     * 로직 관련
     */
    private String flag; // 생성/수정/삭제 flag
    private String bbs_cd_cg; // 게시판 코드 변경 여부
    private String read_yn; // 읽기 권한
    private String write_yn; // 쓰기 권한
    private String update_yn; // 수정 권한
    private String delete_yn; // 삭제 권한
}
