<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="isAuthenticated()">
    <sec:authentication var="USER_DETAIL" property="details"/>
</sec:authorize>

<script type="text/javascript" src="/resources/ckeditor/ckeditor.js"></script>

<div class="article_div">
    <script type="text/javascript">
        $(document).ready(function() {
            window.ParsleyConfig = {
                excluded: 'input[type=button], input[type=submit], input[type=reset]',
                inputs: 'input, textarea, select, input[type=hidden], :hidden'
            };
            $('#articleForm').parsley(); // vo 에 값이 있으면 해당하는 태그에 값 대입

            CKEDITOR.env.isCompatible = true;
        });
    </script>
    <p class="article_bbs_title">${bbsInfo.bbs_nm}</p>
    <form:form modelAttribute="articleVO" id="articleForm" name="articleForm" action="/article/${bbsInfo.bbs_cd}/proc" method="post" data-parsley-validate="true" enctype="multipart/form-data">
        <form:hidden path="bbs_cd" value="${bbsInfo.bbs_cd}"/>
        <form:hidden path="bbs_info_seq" value="${bbsInfo.bbs_info_seq}"/>
        <form:hidden path="flag" value="${empty articleVO.article_seq ? 'c' : 'u'}"/>
        <form:hidden path="article_seq"/>
        <form:hidden path="del_file_count" value="0"/>
        <div class="article_form_info">
            <table class="article_form_info_table">
                <c:choose>
                    <c:when test="${bbsInfo.notice_use_yn eq 'Y' and USER_DETAIL.user_auth eq 'ROLE_ADMIN'}">
                        <tr>
                            <th>공지 여부</th>
                            <td>
                                <label><input name="notice_yn" type="radio" value="Y" ${articleVO.notice_yn eq 'Y' ? 'checked' : ''} required="true"/>공지</label>
                                <label><input name="notice_yn" type="radio" value="N" ${articleVO.notice_yn eq 'N' ? 'checked' : ''} required="true"/>-</label>
                            </td>
                        </tr>
                    </c:when>
                <c:otherwise>
                    <form:hidden path="notice_yn" value="${empty articleVO.notice_yn ? 'N': articleVO.notice_yn }" />
                </c:otherwise>
            </c:choose>
                <c:if test="${not empty articleVO.article_seq}">
                    <tr>
                        <th>작성자</th>
                        <td><c:out value="${articleVO.inpt_user_name}" /></td>
                    </tr>
                </c:if>
                <tr>
                    <th>제목</th>
                    <td><form:input path="subject" type="text"  maxlength="20" placeholder="제목을 입력해주세요" cssClass="data-parsley-required" required="true"/></td>
                </tr>
            </table>
        </div>
        <div>
            <div>
                <label>내용</label>
                <form:textarea path="content" cssClass="ckeditor" data-parsley-required="true" />
            </div>
            <c:if test="${bbsInfo.attach_file_use_yn eq 'Y'}">
                <div class="article_form_attach">
                    <span>첨부파일</span>
                    <div class="article_form_attach_file_div">
                        <c:choose>
                            <c:when test="${not empty files}">
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
            </c:if>
        </div>
    </form:form>
    <div class="btn_div">
        <a href="/article/${bbsInfo.bbs_cd}/list" class="a_btn_small">목록</a>
        <c:choose>
            <c:when test="${empty articleVO.article_seq}">
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