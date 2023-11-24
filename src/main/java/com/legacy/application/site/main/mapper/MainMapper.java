package com.legacy.application.site.main.mapper;

//import com.legacy.config.annotation.Mapper;

import com.legacy.application.site.main.vo.MenuVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

/**
 * 메인 Mapper
 * @author mrmin
 *
 */
@Mapper("mainMapper")
public interface MainMapper {

    List<MenuVO> getMenuList() throws Exception;
}
