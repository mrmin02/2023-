<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="header">
    <div class="header_in">
        <div class="logo_div"><a href="/mng/bbs/list"><img src="/resources/images/spring framework logo_white.png"/></a></div>
        <div>관리자 페이지</div>
        <div class="user_auth_div">
            <sec:authorize access="isAuthenticated()">
                <sec:authentication var="USER_DETAIL" property="details"/>
                <%--				<label><c:out value="${USER_DETAIL.user_name}"/> 님</label>--%>
                <span style="display: block">${USER_DETAIL.user_name}</span>
                <a href="/logout" >로그아웃</a>
            </sec:authorize>
        </div>
    </div>
</div>