<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--<script type="text/javascript" src="/resources/monarch/widgets/parsley/parsley.js"/>--%>

<div class="mng_bbs_form">
    <form:form modelAttribute="htmlMngVO" name="htmlForm" action="/mng/html/form" method="post">
        <form:hidden path="flag" value="${empty htmlMngVO.html_seq ? 'c' : 'u'}"/>
        <form:hidden path="html_seq"/>
    </form:form>


    <h3 class="mng_bbs_info">HTML 상세정보</h3>
    <div class="mng_info_div">
        <div class="mng_info_flex">
            <div class="info_title">제목</div>
            <div class="info_content">
                <c:out value="${htmlMngVO.html_title}"/>
            </div>
        </div>
    </div>
    <div class="mng_info_div">
        <div class="mng_info_flex">
            <div class="info_title">내용</div>
            <div class="info_content">
                ${htmlMngVO.html_content}
            </div>
        </div>
    </div>
    <div class="mng_info_div">
        <div class="mng_info_flex">
            <div class="info_title">작성일</div>
            <div class="info_content">
                <c:out value="${htmlMngVO.inpt_date}"/>
            </div>
        </div>
    </div>
    <div class="mng_info_div">
        <div class="mng_info_flex">
            <div class="info_title">수정일</div>
            <div class="info_content">
                <c:out value="${htmlMngVO.upd_date}"/>
            </div>
        </div>
    </div>

    <div class="btn_div">
        <a href="/mng/html/list" class="a_btn_green">목록</a>
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
                    $("form[name=htmlForm]").attr("action","/mng/html/proc");
                }

            }

            $("[name=flag]").val(flag);
            $("form[name=htmlForm]").submit();
        }
    </script>

</div>