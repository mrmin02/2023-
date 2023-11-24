<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script type="text/javascript" src="/resources/ckeditor/ckeditor.js"></script>

<script type="text/javascript">
    $(document).ready(function() {
        window.ParsleyConfig = {
            excluded: 'input[type=button], input[type=submit], input[type=reset]',
            inputs: 'input, textarea, select, input[type=hidden], :hidden'
        };
        $('#htmlForm').parsley(); // vo 에 값이 있으면 해당하는 태그에 값 대입

        var ckeditor = CKEDITOR.replace("html_content", {
            height: '400px',
            skin: 'moono-lisa'
        });
    });
</script>

<div class="mng_bbs_form">

    <form:form modelAttribute="htmlMngVO" id="htmlForm" name="htmlForm" action="/mng/html/proc" method="post" data-parsley-validate="true">
        <form:hidden path="flag" value="${empty htmlMngVO.html_seq ? 'c' : 'u'}"/>
        <form:hidden path="html_seq"/>

        <h3 class="mng_bbs_info">HTML 등록</h3>

        <div class="mng_info_div">
            <div class="mng_info_flex">
                <div class="info_title">제목</div>
                <div class="info_content">
                    <form:input path="html_title" type="text"  maxlength="20" placeholder="제목을 입력해주세요" cssClass="data-parsley-required info_input" required="true"/>
                </div>
            </div>
        </div>
        <div class="mng_info_div">
            <div class="mng_info_flex">
                <div class="info_title">내용</div>
                <div class="info_content">
                    <form:textarea path="html_content" cssClass="form-control ckeditor" data-parsley-required="true" />
                </div>
            </div>
        </div>
        <div class="mng_info_div">
            <div class="mng_info_flex">
                <div class="info_title">삭제 여부</div>
                <div class="info_content">
                    <label><input name="del_yn" type="radio" value="N" ${empty htmlMngVO.html_seq ? 'checked' : htmlMngVO.del_yn eq 'N' ? 'checked' : ''} required="true"/>-</label>
                    <label><input name="del_yn" type="radio" value="Y" ${htmlMngVO.del_yn eq 'Y' ? 'checked' : ''} required="true"/>삭제</label>
                </div>
            </div>
        </div>
    </form:form>
    <div class="btn_div">
        <a href="/mng/html/list" class="a_btn_green">목록</a>
        <c:choose>
            <c:when test="${empty htmlMngVO.html_seq}">
                <a href="javascript:fn_proc('c');" class="a_btn_blue">확인</a>
            </c:when>
            <c:otherwise>
                <a href="javascript:fn_proc('u');" class="a_btn_blue">수정</a>
                <a href="javascript:fn_proc('d');" class="a_btn_red">삭제</a>
            </c:otherwise>
        </c:choose>
    </div>
    <script type="text/javascript">

        function fn_proc(flag){

            if(flag != 'd'){
                if(!CKEDITOR.instances.html_content.getData()) {
                    alert('내용을 입력해 주세요.');
                    CKEDITOR.instances.html_content.focus();
                    return false;
                }
            }

            var msg = flag == 'c' ? "작성" : flag == 'u' ? "수정" : "삭제";
            var confirm_msg = "HTML을 " + msg + " 하시겠습니까?";

            if(!confirm(confirm_msg)){
                return false;
            }

            $('[name=html_content]').val(CKEDITOR.instances.html_content.getData());
            $("[name=flag]").val(flag);
            $("#htmlForm").submit();
        }

    </script>
</div>


