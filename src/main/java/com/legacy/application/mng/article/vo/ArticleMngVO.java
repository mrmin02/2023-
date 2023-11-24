package com.legacy.application.mng.article.vo;

import com.legacy.application.mng.bbs.vo.BbsMngVO;
import lombok.Data;

@Data
public class ArticleMngVO extends BbsMngVO {

    private String article_seq; // 게시글 순번
    private String subject; // 제목
    private String notice_yn; // 공지사항 여부
    private String content; // 내용
    private String read_cnt; // 조회수
    private String user_id; // 작성자 id
    private String inpt_user_name; // 작성자 이름
    private String inpt_ip; // 작성자 ip
    private String upd_ip; // 수정자 ip
    private String inpt_date; // 작성일
    private String upd_date; // 수정일
    private String del_yn; // 삭제 여부
}
