package com.legacy.application.common.service;

import com.legacy.application.common.util.CommonUtil;
import lombok.Data;

@Data
public class Pagination {

    private int pageIndex = 1;/** 현재 페이지 **/
    private int pageUnit = 10;/** 페이지 개수 **/
    private int recordCountPerPage = 10; /** 한 페이지에 몇 개 노출되는지 **/
    private int totalCount = 1; /** 글 전체 개수 **/
    private String searchCondition; /** 검색 조건 **/
    private String searchKeyword;/** 검색 키워드 **/
    private String itemUrl = ""; /** 클릭 시, 이동할  url **/


    public Pagination(){}

    public Pagination(int pageIndex, int pageUnit, int recordCountPerPage, int totalCount, String searchCondition, String searchKeyword, String itemUrl){
        this.pageIndex = pageIndex;
        this.pageUnit = pageUnit;
        this.recordCountPerPage = recordCountPerPage;
        this.totalCount = totalCount;
        this.searchCondition = searchCondition;
        this.searchKeyword = searchKeyword;
        this.itemUrl = itemUrl;
    }

    /**
     * 페이징 화면에 출력
     * @return
     */
    public String fn_paging(){

        StringBuffer rtnString = new StringBuffer();

        int totalPage = totalCount / recordCountPerPage; //  글 전체 개수 / 한 페이지에 출력되는 글 수   1000 /10  100 페이지..

        if(totalCount % recordCountPerPage > 0){ // 나머지 값 존재하면 페이지수 증가
            totalPage++;
        }

        int startPage = ((pageIndex-1) / pageUnit) * pageUnit + 1; // 12345  678910 일 떄, 1, 6 -> 이 예시의 pageUnit 은 5
        int endPage = startPage + pageUnit -1;
        if(endPage > totalPage){ // 마지막 페이지가 101 페이지이면, endPage는 110 이니까 동일하게 맟춰줌
            endPage = totalPage;
        }

        String href = itemUrl+"?";
        if(CommonUtil.isNotEmpty(searchCondition) && CommonUtil.isNotEmpty(searchKeyword)){
            href = href + "searchCondition="+searchCondition+"&searchKeyword="+searchKeyword;
        }

        rtnString.append("<div class='pagination_div'>");

        if(pageIndex > pageUnit){
            rtnString.append("<a href='"+href+"&pageIndex="+(pageIndex-1)+"'>이전</a>");
        }
        for(int i = startPage; i<= endPage; i++){
            if(i == pageIndex){ //현재 페이지
                rtnString.append("<a style='color:red;'>"+i+"</a>");
            }else{
                rtnString.append("<a href='"+href+"&pageIndex="+i+"'>"+i+"</a>");
            }
        }
        if(endPage != totalPage){
            rtnString.append("<a href='"+href+"&pageIndex="+(pageIndex+1)+"'>다음</a>");
        }

        rtnString.append("</div>");


        return rtnString.toString();
    }
}
