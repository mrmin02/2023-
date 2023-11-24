package com.legacy.application.site.main.vo;

import com.legacy.application.common.config.vo.DefaultVO;
import lombok.Data;

@Data
public class MenuVO extends DefaultVO {

    /**
     * 메뉴 정보
     */
    private String menu_seq;
    private String menu_title;
    /**
     *  메뉴타입
     *  MNT_001 : 분류
     *  MNT_002 : 링크
     *  MNT_003 : 게시판
     *  MNT_004 : HTML
     */
    private String menu_type;

    private String menu_link_type;
    private String menu_link;
    private String prt_seq;
    private String menu_depth;
    private String menu_order;

    /**
     * 게시판
     */
    private String article_info;

    /**
     * html
     */
    private String html_info;
}
