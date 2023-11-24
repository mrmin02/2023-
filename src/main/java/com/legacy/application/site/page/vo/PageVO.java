package com.legacy.application.site.page.vo;

import com.legacy.application.common.config.vo.DefaultVO;
import lombok.Data;

@Data
public class PageVO extends DefaultVO {

    private String html_seq; // html 순번
    private String html_title; // 제목
    private String html_content; // 내용

    private String flag;
}
