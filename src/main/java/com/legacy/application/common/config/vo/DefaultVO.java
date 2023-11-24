package com.legacy.application.common.config.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DefaultVO {

    private String inpt_date;
    private String upd_date;
    private String del_yn;

    /**
     * 검색 관련
     */

    /** 검색 조건 **/
    private String searchCondition;

    /** 검색 키워드 **/
    private String searchKeyword;

    /** 현재 페이지 **/
    private int pageIndex = 1;

    /** 페이지 개수 **/
    private int pageUnit = 10;

    /** 한 페이지에 몇 개 노출되는지 **/
    private int recordCountPerPage = 10;

    /** 글 전체 개수 **/
    private int totalCount = 1;

    private int firstIndex;
    private int lastIndex;

    private int rNum = 1; // 쿼리 관련

    /**
     * 첨부파일 관련
     */
    private MultipartFile[] file; // 파일

    private String file_seq; // 파일 순번
    private String file_nm; // 파일 이름
    private String file_sys_nm; // 저장된 파일 이름
    private String file_path; // 파일 경로

    private String[] del_file_seq; // 삭제할 파일 순번
    private int del_file_count; // 삭제할 파일 개수

    public int getFirstIndex() {
        firstIndex = ((this.getPageIndex()-1)*this.getRecordCountPerPage()) + 1 ;
        return firstIndex;
    }
    public int getLastIndex() {
        lastIndex = ((this.getPageIndex()-1) * this.getRecordCountPerPage() + this.getRecordCountPerPage());
        return lastIndex;
    }

    public int getrNum() {
        return this.rNum;
    }
    public void setrNum(int rNum){
        this.rNum = rNum;
    }
}
