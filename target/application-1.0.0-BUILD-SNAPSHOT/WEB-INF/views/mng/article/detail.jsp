<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--<script type="text/javascript" src="/resources/monarch/widgets/parsley/parsley.js"/>--%>

<div class="mng_bbs_form">
    <form:form modelAttribute="articleMngVO" name="articleForm" action="/mng/article/${bbsInfo.bbs_cd}/form" method="post">
        <form:hidden path="flag" value="${empty articleMngVO.article_seq ? 'c' : 'u'}"/>
        <form:hidden path="article_seq"/>
    </form:form>


    <h3 class="mng_bbs_info">${bbsInfo.bbs_nm} 상세정보</h3>
    <div class="mng_info_div">
        <div class="mng_info_flex">
            <div class="info_title">제목</div>
            <div class="info_content">
                <c:out value="${articleMngVO.subject}"/>
            </div>
        </div>
    </div>
    <c:if test="${articleMngVO.notice_use_yn eq 'Y'}">
        <div class="mng_info_div">
            <div class="mng_info_flex">
                <div class="info_title">공지 여부</div>
                <div class="info_content">
                    <c:out value="${articleMngVO.notice_yn eq 'Y' ? '공지' : '-'}"/>
                </div>
            </div>
        </div>
    </c:if>
    <div class="mng_info_div">
        <div class="mng_info_flex">
            <div class="info_title">작성자</div>
            <div class="info_content">
                <c:out value="${articleMngVO.inpt_user_name}"/>
            </div>
        </div>
    </div>
    <div class="mng_info_div">
        <div class="mng_info_flex">
            <div class="info_title">내용</div>
            <div class="info_content">
                ${articleMngVO.content}
            </div>
        </div>
    </div>
    <div class="mng_info_div">
        <div class="mng_info_flex">
            <div class="info_title">첨부파일</div>
            <div class="info_content">
                <c:forEach var="item" items="${files}">
                    <div>
                        <label><a href="/cmmn/fileDownSimple?fileSeq=${item.file_seq}" target="_blank">${item.file_nm}</a></label>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <div class="mng_info_div">
        <div class="mng_info_flex">
            <div class="info_title">작성일</div>
            <div class="info_content">
                <c:out value="${articleMngVO.inpt_date}"/>
            </div>
        </div>
    </div>
    <div class="mng_info_div">
        <div class="mng_info_flex">
            <div class="info_title">수정일</div>
            <div class="info_content">
                <c:out value="${articleMngVO.upd_date}"/>
            </div>
        </div>
    </div>

    <div class="btn_div">
        <a href="/mng/article/${bbsInfo.bbs_cd}/list" class="a_btn_green">목록</a>
        <a href="javascript:fn_proc('u')" class="a_btn_blue">수정</a>
        <a href="javascript:fn_proc('d')" class="a_btn_red">삭제</a>
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
                    $("form[name=articleForm]").attr("action","/mng/article/${bbsInfo.bbs_cd}/proc");
                }

            }

            $("[name=flag]").val(flag);
            $("form[name=articleForm]").submit();
        }
    </script>

</div>