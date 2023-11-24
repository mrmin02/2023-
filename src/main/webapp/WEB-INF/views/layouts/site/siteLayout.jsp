<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>사용자 페이지</title>

    <script src="/resources/js/jquery/jquery-3.7.1.min.js"></script>
    <script src="/resources/js/jquery/cdn_bootstrap_3.3.2_js_bootstrap.min.js"></script>

    <script type="text/javascript" src="/resources/monarch/widgets/parsley/parsley.js"></script>

    <link rel="stylesheet" href="/resources/css/cdn_bootstrap_3.3.2_css_bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/cdn_bootstrap_3.3.2_css_bootstrap-theme.min.css">

    <link rel="stylesheet" href="/resources/css/sub.css">
    <script type="text/javascript" src="/resources/js/common.js"></script>
</head>
<body>
    <div class="header">
        <t:insertAttribute name="header"/>
    </div>
    <div class="header_sub">
        <t:insertAttribute name="header_sub"/>
    </div>
    <div class="content">
        <t:insertAttribute name="content"/>
    </div>
    <div class="footer">
        <t:insertAttribute name="footer"/>
    </div>
    <c:if test="${not empty rMsg}">
        <script type="text/javascript">
            $(document).ready(function () {
                alert('${rMsg}');
            });
        </script>
    </c:if>
</body>
</html>