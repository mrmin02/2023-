package com.legacy.application.site.page.mapper;

import com.legacy.application.site.page.vo.PageVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("pageMapper")
public interface PageMapper {

    PageVO getPageDetail(PageVO pageVO) throws Exception;
}
