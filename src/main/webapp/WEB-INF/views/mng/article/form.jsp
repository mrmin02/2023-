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
        $('#articleForm').parsley(); // vo 에 값이 있으면 해당하는 태그에 값 대입

    });
</script>

<div class="mng_bbs_form">

    <form:form modelAttribute="articleMngVO" id="articleForm" name="articleForm" action="/mng/article/${bbsInfo.bbs_cd}/proc" method="post" data-parsley-validate="true" enctype="multipart/form-data">
        <form:hidden path="bbs_cd" value="${bbsInfo.bbs_cd}"/>
        <form:hidden path="bbs_info_seq" value="${bbsInfo.bbs_info_seq}"/>
        <form:hidden path="flag" value="${empty articleMngVO.article_seq ? 'c' : 'u'}"/>
        <form:hidden path="article_seq"/>
        <form:hidden path="del_file_count" value="0"/>

        <h3 class="mng_bbs_info">${bbsInfo.bbs_nm} 게시글 등록</h3>
        <c:choose>
            <c:when test="${bbsInfo.notice_use_yn eq 'Y'}">
                <div class="mng_info_div">
                    <div class="mng_info_flex">
                        <div class="info_title">공지 여부</div>
                        <div class="info_content">
                            <label><input name="notice_yn" type="radio" value="Y" ${articleMngVO.notice_yn eq 'Y' ? 'checked' : ''} required="true"/>공지</label>
                            <label><input name="notice_yn" type="radio" value="N" ${articleMngVO.notice_yn eq 'N' ? 'checked' : ''} required="true"/>-</label>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <form:hidden path="notice_yn" value="${empty articleMngVO.notice_yn ? 'N': articleMngVO.notice_yn }" />
            </c:otherwise>
        </c:choose>
        <c:if test="${not empty articleMngVO.article_seq}">
            <div class="mng_info_div">
                <div class="mng_info_flex">
                    <div class="info_title">작성자</div>
                    <div class="info_content">
                        <form:input path="inpt_user_name" type="text"  maxlength="20" placeholder="작성자 이름을 입력해주세요" cssClass="data-parsley-required info_input" required="true"/>
                    </div>
                </div>
            </div>
        </c:if>
        <div class="mng_info_div">
            <div class="mng_info_flex">
                <div class="info_title">제목</div>
                <div class="info_content">
                    <form:input path="subject" type="text"  maxlength="20" placeholder="제목을 입력해주세요" cssClass="data-parsley-required info_input" required="true"/>
                </div>
            </div>
        </div>
        <div class="mng_info_div">
            <div class="mng_info_flex">
                <div class="info_title">내용</div>
                <div class="info_content">
                    <form:textarea path="content" cssClass="form-control ckeditor" data-parsley-required="true" />
                </div>
            </div>
        </div>
        <c:if test="${bbsInfo.attach_file_use_yn eq 'Y'}">
            <div class="mng_info_div">
                <div class="mng_info_flex">
                    <div class="info_title">첨부파일</div>
                    <div class="info_content">
                        <c:choose>
                            <c:when test="${not empty files}">
                                <%--                        <c:forEach  begin="0" end="${articleMngVO.file_cnt -1}" varStatus="idx">--%>
                                <%--                            <c:if test=""--%>
                                <%--                        </c:forEach>--%>

                                <c:forEach  begin="0" end="${bbsInfo.file_cnt -1}" varStatus="idx">
                                    <c:set var="item" value="${files[idx.count-1]}"/>
                                    <c:choose>
                                        <c:when test="${empty item}">
                                            <div>
                                                <form:input type="file" path="file"/>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div>
                                                <label><a href="/cmmn/fileDownSimple?fileSeq=${item.file_seq}">${item.file_nm}</a></label><button type="button" onclick="fn_delFile(this,'${item.file_seq}')">삭제</button>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <c:forEach  begin="0" end="${bbsInfo.file_cnt -1}" varStatus="idx">
                                    <div>
                                        <form:input type="file" path="file"/>
                                    </div>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </c:if>
    </form:form>
    <div class="btn_div">
        <a href="/mng/article/${bbsInfo.bbs_cd}/list" class="a_btn_green">목록</a>
        <c:choose>
            <c:when test="${empty articleMngVO.article_seq}">
                <a href="javascript:fn_proc('c');" class="a_btn_blue">확인</a>
            </c:when>
            <c:otherwise>
                <a href="javascript:fn_proc('u');" class="a_btn_blue">수정</a>
                <a href="javascript:fn_proc('d');" class="a_btn_red">삭제</a>
            </c:otherwise>
        </c:choose>
    </div>
    <script type="text/javascript">
        var del_file_cnt = 0;
        function fn_proc(flag){

            if(flag != 'd'){
                if(!CKEDITOR.instances.content.getData()) {
                    alert('내용을 입력해 주세요.');
                    CKEDITOR.instances.content.focus();
                    return false;
                }
            }

            var msg = flag == 'c' ? "작성" : flag == 'u' ? "수정" : "삭제";
            var confirm_msg = "게시글을 " + msg + " 하시겠습니까?";

            if(!confirm(confirm_msg)){
                return false;
            }

            $('[name=content]').val(CKEDITOR.instances.content.getData());
            $("[name=flag]").val(flag);
            $("#articleForm").submit();
        }

        function fn_delFile(e,file_seq){
            $(e).parent().html("<input type='file' name='file'><input type='hidden' name='del_file_seq' value='"+file_seq+"'/>");
            del_file_cnt++;
            $("[name=del_file_cnt]").val(del_file_cnt);
        }
    </script>
</div>


