package com.legacy.application.mng.menu.mapper;

import com.legacy.application.mng.menu.vo.MenuMngVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.awt.*;
import java.util.List;

/**
 * 메뉴 관리 mapper
 */
@Mapper("menuMngMapper")
public interface MenuMngMapper {

    /**
     * 게시판 리스트
     * @param menuMngVO
     * @return
     * @throws Exception
     */
    List<MenuMngVO> getBbsList(MenuMngVO menuMngVO) throws Exception;

    /**
     * html 리스트
     * @param menuMngVO
     * @return
     * @throws Exception
     */
    List<MenuMngVO> getHtmlList(MenuMngVO menuMngVO) throws Exception;

    /**
     * 메뉴 저장
     * @param menuMngVO
     * @return
     * @throws Exception
     */
    int setMenu(MenuMngVO menuMngVO) throws Exception;

    MenuMngVO getNowMenuJson() throws Exception;

    int setMenuBackup(MenuMngVO menuMngVO) throws Exception;

    int delMenu() throws Exception;
}
