package com.legacy.application.mng.menu.service;

import com.legacy.application.common.util.CommonUtil;
import com.legacy.application.mng.menu.mapper.MenuMngMapper;
import com.legacy.application.mng.menu.vo.MenuMngVO;

import org.springframework.stereotype.Service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * menu 관리 service
 */
@Service("menuMngService")
public class MenuMngService {

    @Resource(name = "menuMngMapper")
    MenuMngMapper menuMngMapper;

    public String getNowJson() throws Exception{
        MenuMngVO now_vo = menuMngMapper.getNowMenuJson();

        if(now_vo != null){
            return now_vo.getMenu_json();
        }else{
            return "";
        }
    }

    /**
     * 메뉴 타입에 따라 게시판, html 리스트 반환
     * @param menuMngVO
     * @return
     * @throws Exception
     */
    public List<MenuMngVO> getMenuTypeList(MenuMngVO menuMngVO) throws Exception{

        if(menuMngVO.getMenu_type() != null){
            switch (menuMngVO.getMenu_type()){
                case "MNT_003": // 게시판
                    return menuMngMapper.getBbsList(menuMngVO);
                case "MNT_004": // HTML
                    return menuMngMapper.getHtmlList(menuMngVO);
                default:
                    return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 메뉴 등록/수정
     * @param menuMngVO
     * @return
     * @throws Exception
     */
    public Map<String,Object> menuProc(MenuMngVO menuMngVO) throws Exception{
        Map<String,Object> map = new HashMap<>();
        boolean result = false;
        String rMsg = "";

        // 메뉴 백업 json 만
        backupMenu();

        // 메뉴 전체 삭제
        delMenu();

        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(menuMngVO.getMenu_json());

        setMenuJson(jsonArray, menuMngVO);

        return map;
    }

    /**
     * json 형태 메뉴 저장
     * @param jsonArray
     * @param menuMngVO
     * @throws Exception
     */
    void setMenuJson(JSONArray jsonArray, MenuMngVO menuMngVO) throws Exception{

        for (Object obj : jsonArray){
            JSONParser jsonParser = new JSONParser();
            JSONObject json = (JSONObject) jsonParser.parse(obj.toString());
            menuMngVO.setMenu_seq(json.get("id").toString());
            menuMngVO.setMenu_title(json.get("menu_title").toString());
            menuMngVO.setMenu_type(json.get("menu_type").toString());

            if(json.get("menu_link_type") != null){
                menuMngVO.setMenu_link_type(json.get("menu_link_type").toString());
            }else{
                menuMngVO.setMenu_link_type("LKT_001");
            }

            menuMngVO.setMenu_link(json.get("menu_link").toString());
            menuMngVO.setMenu_depth(json.get("depth").toString());
            menuMngVO.setMenu_order(json.get("order").toString());

            if(json.get("parent").toString().equals("null")){
                menuMngVO.setPrt_seq(null);
            }else{
                menuMngVO.setPrt_seq(json.get("parent").toString());
            }

            if(CommonUtil.isNotEmpty(json.get("article_info"))){
                if(json.get("article_info").toString().equals("undefined")){
                    menuMngVO.setArticle_info("#");
                }else{
                    menuMngVO.setArticle_info(json.get("article_info").toString());
                }
            }else{
                menuMngVO.setArticle_info("#");
            }
            if(CommonUtil.isNotEmpty(json.get("html_info"))){
                if(json.get("html_info").toString().equals("undefined")){
                    menuMngVO.setHtml_info("#");
                }else{
                    menuMngVO.setHtml_info(json.get("html_info").toString());
                }
            }else{
                menuMngVO.setHtml_info("#");
            }
            // 저장 함수
            menuMngMapper.setMenu(menuMngVO);

            if(json.get("children") != null) {
                menuMngVO.setMenu_json("");
                JSONArray childArray = (JSONArray) jsonParser.parse(json.get("children").toString());
                setMenuJson(childArray, menuMngVO);
            }
        }
    }

    void backupMenu() throws Exception{
        MenuMngVO now_vo = menuMngMapper.getNowMenuJson();

        if(now_vo != null){
            menuMngMapper.setMenuBackup(now_vo);
        }
    }

    void delMenu() throws Exception{
        menuMngMapper.delMenu();
    }
}
