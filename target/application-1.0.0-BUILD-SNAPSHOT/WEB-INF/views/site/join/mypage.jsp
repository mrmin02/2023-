<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="user_form_div">
    <sec:authorize access="isAuthenticated()">
        <sec:authentication var="USER_DETAIL" property="details"/>
    </sec:authorize>
    <form:form modelAttribute="loginVO" name="mypageForm" action="/mypage/proc" method="post">
        <form:hidden path="user_seq" value="${USER_DETAIL.user_seq}"/>
        <form:hidden path="flag" />
        <table class="user_form_table">
            <tr>
                <th>이름</th>
                <td><c:out value="${USER_DETAIL.user_name}"/></td>
            </tr>
            <tr>
                <th>아이디</th>
                <td><c:out value="${USER_DETAIL.user_id}"/></td>
            </tr>
            <tr>
                <th>비밀번호</th>
                <td><form:input type="password" path="user_pwd" placeholder="비밀번호를 입력해주세요." maxlength="30"/></td>
            </tr>
            <tr>
                <th>비밀번호 확인</th>
                <td><form:input type="password" path="user_pwd_re" placeholder="비밀번호를 입력해주세요." maxlength="30"/></td>
            </tr>
        </table>
<%--        <div>--%>
<%--            <div>--%>
<%--                <label>이름 : </label>--%>
<%--                <label><c:out value="${USER_DETAIL.user_name}"/> </label>--%>
<%--            </div>--%>
<%--            <div>--%>
<%--                <label>아이디 : </label>--%>
<%--                <label><c:out value="${USER_DETAIL.user_id}"/> </label>--%>
<%--            </div>--%>
<%--            <div>--%>
<%--                <label>비밀번호</label><form:input type="password" path="user_pwd" placeholder="비밀번호를 입력해주세요." maxlength="30"/>--%>
<%--            </div>--%>
<%--            <div>--%>
<%--                <label>비밀번호 확인</label><form:input type="password" path="user_pwd_re" placeholder="비밀번호를 입력해주세요." maxlength="30"/>--%>
<%--            </div>--%>
<%--        </div>--%>
        <div>
            <a href="/main" class="a_btn_blue">메인으로</a>
            <a href="#" type="button" onclick="mypageProc('u')" class="a_btn_red" style="float: right">수정</a>
        </div>
        <div style="margin-top: 100px">
            <a href="/mypage/del_form">회원탈퇴</a>
        </div>
    </form:form>
    <script>
        /**
         * 회원정보 변경
         */
        function mypageProc(flag){
            // 비밀번호 검사
            if($("[name=user_pwd]").val().length == 0 ){
                alert("비밀번호를 입력해주세요.");
                return false;
            }
            if($("[name=user_pwd_re]").val().length == 0 ){
                alert("비밀번호 확인를 입력해주세요.");
                return false;
            }
            if($("[name=user_pwd]").val() != $("[name=user_pwd_re]").val()){
                alert("비밀번호와 비밀번호 확인이 다릅니다.");
                return false;
            }

            if(!confirm("회원정보를 수정하시겠습니까?")){
                return false;
            }

            $("[name=flag]").val(flag);
            $("form[name=mypageForm]").submit();
        }
    </script>

</div>