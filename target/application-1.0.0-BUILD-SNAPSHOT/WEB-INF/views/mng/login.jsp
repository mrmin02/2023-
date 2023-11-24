<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
    <title>관리자 로그인</title>
    <script src="/resources/js/jquery/jquery-3.7.1.min.js"></script>
    <script src="/resources/js/jquery/cdn_bootstrap_3.3.2_js_bootstrap.min.js"></script>
    <link rel="stylesheet" href="/resources/css/cdn_bootstrap_3.3.2_css_bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/cdn_bootstrap_3.3.2_css_bootstrap-theme.min.css">

</head>
<body>
<p>관리자 로그인 페이지 입니다.</p>
<form:form modelAttribute="loginVO" id="loginForm" action="/login/proc" method="post">
    <form:input path="user_id" maxlength="20" placeholder="아이디를 입력해주세요."/>
    <form:input path="user_pwd" maxlength="20" type="password" placeholder="비밀번호를 입력해주세요."/>
    <a href="javascript:fn_login()" type="button" >로그인</a>
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
</body>
</html>
