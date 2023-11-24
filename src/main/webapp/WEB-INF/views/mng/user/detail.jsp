<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div>
    <form:form modelAttribute="userMngVO" name="userForm" action="/mng/user/form" method="post">
        <form:hidden path="user_seq" value="${userMngVO.user_seq}"/>
        <form:hidden path="flag"/>
    </form:form>

    <div class="mng_bbs_form">
        <div class="mng_bbs_info">
            <h3 class="mng_bbs_info">회원 상세정보</h3>
            <div class="mng_info_div">
                <div class="mng_info_flex">
                    <div class="info_title">회원 시퀀스</div>
                    <div class="info_content">
                        <c:out value="${userMngVO.user_seq}"/>
                    </div>
                </div>
            </div>
            <div class="mng_info_div">
                <div class="mng_info_flex">
                    <div class="info_title">아이디</div>
                    <div class="info_content">
                        <c:out value="${userMngVO.user_id}"/>
                    </div>
                </div>
            </div>
            <div class="mng_info_div">
                <div class="mng_info_flex">
                    <div class="info_title">이름</div>
                    <div class="info_content">
                        <c:out value="${userMngVO.user_name}"/>
                    </div>
                </div>
            </div>
            <div class="mng_info_div">
                <div class="mng_info_flex">
                    <div class="info_title">권한</div>
                    <div class="info_content">
                        ${userMngVO.user_auth eq 'ROLE_ADMIN' ? '관리자' : '사용자'}
                    </div>
                </div>
            </div>
            <div class="mng_info_div">
                <div class="mng_info_flex">
                    <div class="info_title">탈퇴 여부</div>
                    <div class="info_content">
                        <c:out value="${userMngVO.del_yn}"/>
                    </div>
                </div>
            </div>
            <div class="mng_info_div">
                <div class="mng_info_flex">
                    <div class="info_title">가입일</div>
                    <div class="info_content">
                        <c:out value="${userMngVO.inpt_date}"/>
                    </div>
                </div>
            </div>
            <div class="mng_info_div">
                <div class="mng_info_flex">
                    <div class="info_title">수정일</div>
                    <div class="info_content">
                        <c:out value="${userMngVO.upd_date}"/>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="btn_div">
        <a href="/mng/user/list" class="a_btn_green">목록</a>
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
                    $("form[name=userForm]").attr("action","/mng/user/proc");
                }

            }

            $("[name=flag]").val(flag);
            $("form[name=userForm]").submit();
        }
    </script>
</div>