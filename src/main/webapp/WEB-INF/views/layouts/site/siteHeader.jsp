<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    $(document).ready(function () {

        // 사이트 상단 메뉴 세팅
        $.ajax({
            url:"/ajax/menu/list",
            type: "post",
            dataType: 'json',
            success: function (res) {
                if (res.length) {
                    // 대 메뉴
                    var topMenu = "";
                    var subMenu = "";

                    res.forEach(function(item, idx){

                        var href= "#none";
                        var target = "";

                        if(item.menu_link_type == "LKT_002"){// 새창
                            target = "_blank";
                        }else{ // 현재창
                            target = "_self";
                        }

                        switch (item.menu_type){
                            case "MNT_001": // 분류
                                href = "#none";
                                break;
                            case "MNT_002": // 링크
                                href = item.menu_link;
                                break;
                            case "MNT_003": // 게시판
                                href = "/article/" + item.article_info + "/list";
                                break;
                            case "MNT_004": //HTML
                                href = "/page/" + item.html_info;
                                break;
                        }

                        if(item.menu_depth == 0){ // 대메뉴 1depth

                            topMenu += "<li><a href='" + href + "' target='" + target + "'>"+ item.menu_title +"</a></li>";
                            if(idx != 0){
                                subMenu += "</ul>";
                            }
                            subMenu += "<ul class='submenu'>";
                        }else{ // 서브메뉴 2depth

                            subMenu += "<li><a href='" + href + "' target='" + target + "'>"+ item.menu_title +"</a></li>";
                            if(res.length-1 == idx){
                                subMenu += "</ul>";
                            }
                        }
                    });
                    $(".menu").empty();
                    $(".menu").append($(topMenu));
                    $(".sub_flex_div").empty();
                    $(".sub_flex_div").append($(subMenu));
                }
            }
        });
    });
</script>

<div class="header_in">
    <div class="logo_div">
        <a href="/main"><img src="/resources/images/spring framework logo_white.png"/></a>
    </div>
    <div class="menu_div">
        <ul class="menu">
<%--            <li><a href="#">공지사항</a></li>--%>
        </ul>
    </div>
    <div class="user_auth_div">
        <sec:authorize access="isAnonymous()">
            <a href="/login">로그인</a><br/>
            <a href="/join/policy">회원가입</a>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <sec:authentication var="USER_DETAIL" property="details"/>
            <%--				<label><c:out value="${USER_DETAIL.user_name}"/> 님</label>--%>
            <a href="/mypage/form" >마이페이지</a>
            <a href="/logout" >로그아웃</a>
        </sec:authorize>
    </div>
</div>
<div class="header_on">
    <div class="menu_flex_div">
        <div class="logo_div"><a href="/main"><img src="/resources/images/spring framework logo.png"/></a></div>
        <div class="menu_div">
            <ul class="menu">
<%--                <li><a href="#">공지사항</a></li>--%>
            </ul>
        </div>
        <div class="user_auth_div">
            <sec:authorize access="isAnonymous()">
                <a href="/login">로그인</a><br/>
                <a href="/join/policy">회원가입</a>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <sec:authentication var="USER_DETAIL" property="details"/>
                <%--					<label><c:out value="${USER_DETAIL.user_name}"/> 님</label>--%>
                <a href="/mypage/form" >마이페이지</a>
                <a href="/logout" >로그아웃</a>
            </sec:authorize>
        </div>
    </div>
    <div class="sub_menu_div">
        <div class="sub_flex_div">
<%--            <ul class="submenu">--%>
<%--                <li><a href="/article/NOTICE/list">공지사항</a></li>--%>
<%--            </ul>--%>
        </div>
    </div>
</div>