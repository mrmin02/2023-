package com.legacy.application.site.article.vo;

import com.legacy.application.mng.bbs.vo.BbsMngVO;
import lombok.Data;

@Data
public class ArticleVO extends BbsMngVO {
    private String article_seq; // 게시글 순번
    private String subject; // 제목
    private String notice_yn; // 공지사항 여부
    private String content; // 내용
    private String read_cnt; // 조회수
    private String user_id; // 작성자 id
    private String inpt_user_name; // 작성자 이름
    private String inpt_ip; // 작성자 ip
    private String upd_ip; // 수정자 ip

    public ArticleVO(){}

    public ArticleVO(String bbs_cd){
        super.setBbs_cd(bbs_cd);
    }
}
