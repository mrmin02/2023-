package com.legacy.application.site.page.service;

import com.legacy.application.site.page.mapper.PageMapper;
import com.legacy.application.site.page.vo.PageVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("pageService")
public class PageService {

    @Resource(name = "pageMapper")
    PageMapper pageMapper;

    /**
     * HTML 상세 정보
     * @param pageVO
     * @return
     * @throws Exception
     */
    public PageVO getPageDetail(PageVO pageVO) throws Exception{
        return pageMapper.getPageDetail(pageVO);
    }
}
