<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="article_detail_div">
    <p class="article_bbs_title">${bbsInfo.bbs_nm}</p>
    <form:form modelAttribute="articleVO" name="articleForm" action="/article/${bbsInfo.bbs_cd}/form" method="post">
        <form:hidden path="flag" value="${empty articleVO.article_seq ? 'c' : 'u'}"/>
        <form:hidden path="article_seq"/>
        <form:hidden path="searchCondition"/>
        <form:hidden path="searchKeyword"/>
        <form:hidden path="pageIndex"/>
    </form:form>
    <div class="article_detail_title_div">
        <div class="article_detail_title">
            <label>${articleVO.notice_yn eq 'Y' ? '[공지] ' : ''}<c:out value="${articleVO.subject}"/></label>
        </div>
        <div class="article_detail_info">
            <span>작성자 <c:out value="${articleVO.inpt_user_name}"/></span>
            <span>조회수 xx</span>
            <span>작성일 ${articleVO.inpt_date}</span>
        </div>
    </div>
    <div class="article_detail_content_div">
            ${articleVO.content}
    </div>
    <c:if test="${bbsInfo.attach_file_use_yn eq 'Y'}">
        <div class="article_detail_attach_div">
            <span>첨부파일</span>
            <c:forEach var="item" items="${files}">
                    <label class="attach_file_label"><a href="/cmmn/fileDownSimple?fileSeq=${item.file_seq}" target="_blank">${item.file_nm}</a></label>
            </c:forEach>
        </div>
    </c:if>
    <div class="btn_div">
        <a href="#none" onclick="fn_goList()" class="a_btn_small">목록</a>
        <c:if test="${user_auth.auth_update}">
            <a href="javascript:fn_proc('u')" class="a_btn_blue">수정</a>
        </c:if>
        <c:if test="${user_auth.auth_delete}">
            <a href="javascript:fn_proc('d')" class="a_btn_red">삭제</a>
        </c:if>
    </div>

    <script>
        function fn_proc(flag){

            if(flag == 'd'){
                var msg = flag == 'u' ? "수정" : "삭제";
                var confirm_msg = msg + " 하시겠습니까?";

                if(!confirm(confirm_msg)){
                    return false;
                }

                if(flag == 'd'){
                    $("form[name=articleForm]").attr("action","/article/${bbsInfo.bbs_cd}/proc");
                }
            }

            $("[name=flag]").val(flag);
            $("form[name=articleForm]").submit();
        }

        function fn_goList(){
            var url = "/article/${bbsInfo.bbs_cd}/list";

            $("[name=flag]").val("r");
            $("form[name=articleForm]").attr("action",url);
            $("form[name=articleForm]").attr("method","get");
            $("form[name=articleForm]").submit();
        }
    </script>
</div>