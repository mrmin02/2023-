<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div>
    <form:form modelAttribute="loginVO" id="loginForm" action="/login/proc" method="post">
        <div class="login_form_div">
            <form:input path="user_id" maxlength="20" cssClass="login_id" placeholder="아이디를 입력해주세요."/>
            <form:input path="user_pwd" maxlength="20" cssClass="login_pwd" type="password" placeholder="비밀번호를 입력해주세요."/>
            <a href="javascript:fn_login()" type="button" class="login_btn" >로그인</a>
        </div>
    </form:form>
    <div class="login_error_msg">
        <c:if test="${not empty loginFailureMsg}">
            <p><c:out value="${loginFailureMsg['rMsg']}"/></p>
            <%session.removeAttribute("loginFailureMsg");%>
        </c:if>
    </div>
    <script>
        function fn_login(){
            var user_id = $("[name=user_id]").val();
            var user_pwd = $("[name=user_pwd]").val();

            if(user_id.length == 0){
                alert("아이디를 입력해주세요.");
                return false;
            }
            if(user_pwd.length == 0){
                alert("비밀번호를 입력해주세요.");
                return false;
            }

            $("form#loginForm").submit();
        }
    </script>
</div>
