<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>관리자 페이지</title>
    <script type="text/javascript" src="/resources/js/jquery/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery/cdn_bootstrap_3.3.2_js_bootstrap.min.js"></script>
<%--    <link rel="stylesheet" href="/resources/css/cdn_bootstrap_3.3.2_css_bootstrap.min.css">--%>
<%--    <link rel="stylesheet" href="/resources/css/cdn_bootstrap_3.3.2_css_bootstrap-theme.min.css">--%>
<%--    <link rel="stylesheet" href="/resources/monarch/widgets/datatable/datatable.css"/>--%>

    <link rel="stylesheet" href="/resources/css/admin.css">
<%--    <link rel="stylesheet" href="/resources/css/nestable.css">--%>

<%--    <script type="text/javascript" src="//cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"/>--%>
<%--    <link rel="stylesheet" href="//cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css"--%>


<%--    <script type="text/javascript" src="/resources/monarch/widgets/parsley/parsley.js"/>--%>


</head>
<body>
    <t:insertAttribute name="header"/>

    <div class="main_content">
        <t:insertAttribute name="menu"/>

        <div class="content">
            <t:insertAttribute name="content"/>
<%--            <div class="content_title">--%>
<%--                <!-- <h3>Hello World..!</h3> -->--%>
<%--            </div>--%>
<%--            <div>--%>
<%--                --%>
<%--            </div>--%>
        </div>
    </div>

<%--    <div class="footer">--%>
<%--        <div class="footer_content">--%>
<%--            <span>Made by : minseok Choi</span>--%>
<%--            <p>Git : github.com/mrmin02</p>--%>
<%--        </div>--%>
<%--    </div>--%>

    <c:if test="${not empty rMsg}">
        <script type="text/javascript">
            $(document).ready(function () {
                alert('${rMsg}');
            });
        </script>
    </c:if>
</body>
</html>