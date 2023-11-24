<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.legacy.application.common.service.Pagination" %>
<%@ page import="com.legacy.application.site.article.vo.ArticleVO" %>

<div class="article_div">
<%--    <p>게시글 목록</p>--%>
    <div>
        <form:form modelAttribute="articleVO" action="" method="get" name="articleListForm">
            <form:hidden path="flag" value="r"/>
            <form:hidden path="pageIndex"/>
            <div class="select_div">
                <select name="searchCondition" id="searchCondition">
                    <option value="TA.SUBJECT" ${empty articleVO.searchCondition or articleVO.searchCondition eq 'TA.SUBJECT' ? 'selected' : ''}>제목</option>
                    <option value="TA.ALL" ${articleVO.searchCondition eq 'TA.ALL' ? 'selected' : ''}>제목+내용</option>
                </select>
                <input type="text" name="searchKeyword" id="searchKeyword" placeholder="검색어를 입력하세요." value="${articleVO.searchKeyword}"/>
                <a href="#none" onclick="fn_goSearch()" class="a_btn_blue">검색</a>
            </div>
        </form:form>
        <c:if test="${user_auth.auth_create}">
            <a href="#none" type="button" onclick="fn_goForm('c')" class="a_btn_red article_write_btn">작성</a>
        </c:if>
        <div>

        </div>

        <table id="listTable" cellspacing="0" width="100%" class="article_table">
<%--            <caption>게시글 리스트</caption>--%>
            <colgroup>
                <col width="8%">
                <col width="*">
                <col width="15%">
                <col width="12%">
                <col width="12%">
            </colgroup>
            <thead>
                <tr>
                    <th>No</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>조회수</th>
                    <th>등록일</th>
                </tr>
            </thead>
            <tbody>
            <c:if test="${not empty noticeList}">
                <c:forEach items="${noticeList}" var="item" varStatus="idx">
                    <tr style="cursor: pointer; background-color: #ECFFDB" onclick="javascript:fn_goDetail('${item.article_seq}')">
                        <td>-</td>
                        <td><label style="font-weight: bold">[공지]</label> ${item.subject}</td>
                        <td>${item.inpt_user_name}</td>
                        <td>${item.read_cnt}</td>
                        <td>${item.inpt_date}</td>
                    </tr>
                </c:forEach>
            </c:if>
            <c:forEach items="${list}" var="list" varStatus="idx">
                <tr style="cursor: pointer;" onclick="javascript:fn_goDetail('${list.article_seq}')">
                    <td>${totalCnt - list.rNum + 1}</td>
                    <td>${list.subject}</td>
                    <td>${list.inpt_user_name}</td>
                    <td>${list.read_cnt}</td>
                    <td>${list.inpt_date}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%--  TODO 페이징  --%>
        <%
            ArticleVO vo = (ArticleVO)request.getAttribute("articleVO");

            int totalCnt = (int)request.getAttribute("totalCnt");

            //현재 페이지
            Pagination pagination = new Pagination(vo.getPageIndex(), vo.getPageUnit(), vo.getRecordCountPerPage(), totalCnt, vo.getSearchCondition(),vo.getSearchKeyword(),"/article/"+vo.getBbs_cd()+"/list");

            String rtnPage = pagination.fn_paging();
            out.print(rtnPage);
        %>

    </div>
    <script>
        var article_code = '${articleVO.bbs_cd}';
        function fn_goDetail(article_seq){

            var url = "/article/"+article_code+"/detail/"+article_seq;
            $("[name=flag]").val("r");
            $("[name=articleListForm]").attr("action",url);
            $("[name=articleListForm]").submit();
        }
        function fn_goForm(flag){
            if(flag == "c"){
                var url = "/article/"+article_code+"/form";
                $("[name=flag]").val("c");
                $("[name=articleListForm]").attr("action",url);
                $("[name=articleListForm]").submit();
            }
        }
        function fn_goSearch(){
            var url = "/article/"+article_code+"/list";
            $("[name=flag]").val("r");
            $("[name=articleListForm]").attr("action",url);
            $("[name=articleListForm]").submit();
        }
    </script>

</div>