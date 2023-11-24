<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
	<title>메인페이지</title>
	<script src="/resources/js/jquery/jquery-3.7.1.min.js"></script>
	<script src="/resources/js/jquery/cdn_bootstrap_3.3.2_js_bootstrap.min.js"></script>
	<link rel="stylesheet" href="/resources/css/cdn_bootstrap_3.3.2_css_bootstrap.min.css">
	<link rel="stylesheet" href="/resources/css/cdn_bootstrap_3.3.2_css_bootstrap-theme.min.css">

	<link rel="stylesheet" href="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.css">
	<script src="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.min.js"></script>

	<link rel="stylesheet" href="/resources/css/main.css">
</head>
<body>
<script>
	$(document).ready(function () {

		// bxslider
		$(".slider").bxSlider({
			mode: 'fade',//horizontal
			adaptiveHeight: true,
			speed: 500,
			pager: false, // 페이지 표시
			moveSlides: 1, // 슬라이드 이동 시 개수
			// slideWidth: 1200, // 슬라이드 너비
			auto: true,
			autoHover: false, // 마우스 호버 시, 정지여부
			controls: false, // 이전 다음버튼 출력 여부
			onSliderLoad: function(){
				$(".slider_div").css("visibility","visible").animate({opacity:1});
			}
		});

		$(".header_in .menu_div").on("mouseover",function(){
			$(".header_on").fadeIn(300);
			$("div.header").css("background-color","#eee").animate({opacity:1});
		});
		$(".header_on").on("mouseleave",function(){
			$(".header_on").fadeOut(300);
			$("div.header").css("background-color","#2E332B").animate({opacity:1});
		});
	});
</script>
<div class="header">
<%--	<div class="header_in">--%>
<%--		<div class="logo_div">--%>
<%--			<a href="/main"><img src="/resources/images/spring framework logo_white.png"/></a>--%>
<%--		</div>--%>
<%--		<div class="menu_div">--%>
<%--			<ul class="menu">--%>
<%--				<li><a href="#">공지사항</a></li>--%>
<%--				<li><a href="#">HTML</a></li>--%>
<%--				<li><a href="#">3번 메뉴</a></li>--%>
<%--				<li><a href="#">4번 메뉴</a></li>--%>
<%--			</ul>--%>
<%--		</div>--%>
<%--		<div class="user_auth_div">--%>
<%--			<sec:authorize access="isAnonymous()">--%>
<%--				<a href="/login">로그인</a><br/>--%>
<%--				<a href="/join/policy">회원가입</a>--%>
<%--			</sec:authorize>--%>
<%--			<sec:authorize access="isAuthenticated()">--%>
<%--				<sec:authentication var="USER_DETAIL" property="details"/>--%>
<%--&lt;%&ndash;				<label><c:out value="${USER_DETAIL.user_name}"/> 님</label>&ndash;%&gt;--%>
<%--				<a href="/mypage/form" >마이페이지</a>--%>
<%--				<a href="/logout" >로그아웃</a>--%>
<%--			</sec:authorize>--%>
<%--		</div>--%>
<%--	</div>--%>
<%--	<div class="header_on">--%>
<%--		<div class="menu_flex_div">--%>
<%--			<div class="logo_div"><a href="/main"><img src="/resources/images/spring framework logo.png"/></a></div>--%>
<%--			<div class="menu_div">--%>
<%--				<ul class="menu">--%>
<%--					<li><a href="#">공지사항</a></li>--%>
<%--					<li><a href="#">HTML</a></li>--%>
<%--					<li><a href="#">3번 메뉴</a></li>--%>
<%--					<li><a href="#">4번 메뉴</a></li>--%>
<%--				</ul>--%>
<%--			</div>--%>
<%--			<div class="user_auth_div">--%>
<%--				<sec:authorize access="isAnonymous()">--%>
<%--					<a href="/login">로그인</a><br/>--%>
<%--					<a href="/join/policy">회원가입</a>--%>
<%--				</sec:authorize>--%>
<%--				<sec:authorize access="isAuthenticated()">--%>
<%--					<sec:authentication var="USER_DETAIL" property="details"/>--%>
<%--&lt;%&ndash;					<label><c:out value="${USER_DETAIL.user_name}"/> 님</label>&ndash;%&gt;--%>
<%--					<a href="/mypage/form" >마이페이지</a>--%>
<%--					<a href="/logout" >로그아웃</a>--%>
<%--				</sec:authorize>--%>
<%--			</div>--%>
<%--		</div>--%>
<%--		<div class="sub_menu_div">--%>
<%--			<div class="sub_flex_div">--%>
<%--				<ul class="submenu">--%>
<%--					<li><a href="/article/NOTICE/list">공지사항</a></li>--%>
<%--				</ul>--%>
<%--				<ul class="submenu">--%>
<%--					<li><a href="/page/1">Html 1번</a></li>--%>
<%--					<li><a href="/page/2">Html 2번</a></li>--%>
<%--					<li><a href="#">2-3</a></li>--%>
<%--					<li><a href="#">2-4</a></li>--%>
<%--					<li><a href="#">2-5</a></li>--%>
<%--				</ul>--%>
<%--				<ul class="submenu">--%>
<%--					<li><a href="#">3-1</a></li>--%>
<%--					<li><a href="#">3-2</a></li>--%>
<%--					<li><a href="#">3-3</a></li>--%>
<%--					<li><a href="#">3-4</a></li>--%>
<%--					<li><a href="#">3-5</a></li>--%>
<%--				</ul>--%>
<%--				<ul class="submenu">--%>
<%--					<li><a href="#">4-1</a></li>--%>
<%--					<li><a href="#">4-2</a></li>--%>
<%--					<li><a href="#">4-3</a></li>--%>
<%--					<li><a href="#">4-4</a></li>--%>
<%--					<li><a href="#">4-5</a></li>--%>
<%--				</ul>--%>
<%--			</div>--%>
<%--		</div>--%>
<%--	</div>--%>
	<jsp:include page="/WEB-INF/views/layouts/site/siteHeader.jsp"/>
</div>
<div class="main_content">
	<!-- bxslider START-->
	<div class="slider_div" style="visibility: hidden;opacity: 0;">
		<div class="slider">
			<div class="image-back" style="background-image: url('/resources/images/main1.jpg');">
				<!-- <span>test1</span> -->
			</div>
			<div class="image-back" style="background-image: url('/resources/images/main2.jpg');">
				<!-- <span>test1</span> -->
			</div>
		</div>
	</div>
	<!-- bxslider END -->
	<div class="content">
		<div class="content_title">
			<h3>Hello World..!</h3>
			<span>스프링 레거시로 만들어진 홈페이지 입니다.</span>
		</div>
		<div class="content_item1">
			<div class="content_article">
				<div class="content_article_title">
					<a href="#" class="article_title_btn">공지사항</a>
					<a href="/article/NOTICE/list" class="more_btn">더보기</a>
				</div>
				<ul class="article_block_ul">
					<c:forEach var="item" items="${noticeList}">
						<li class="article_block">
							<a href="/article/NOTICE/detail/${item.article_seq}">
								<p>
									${item.subject}
								</p>
								<span>${item.inpt_date}</span>
							</a>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="content_item2">
			<div class="content_item2_content">
				<div class="content_git_img"></div>
				<div class="content_git_text">
					<p>이름 : 최민석</p>
					<p>나이 : 99년생 ( 2023년 기준 만24세 )</p>
					<p style="font-size: 20px;">경력</p>
					<p>(주)에스이코리아 2020.10.05 ~ 2023.02.28</p>
					<p style="margin-top: 60px;">github.com/mrmin02</p>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="footer">
	<div class="footer_content">
		<span>Made by : minseok Choi</span>
		<p>Git : github.com/mrmin02</p>
	</div>
</div>


<%--<sec:authorize access="isAnonymous()">--%>
<%--	<a href="/login">로그인 바로가기</a>--%>
<%--	<a href="/join/policy">회원가입</a>--%>
<%--</sec:authorize>--%>
<%--<sec:authorize access="isAuthenticated()">--%>
<%--	<sec:authentication var="USER_DETAIL" property="details"/>--%>
<%--	<label><c:out value="${USER_DETAIL.user_name}"/> 님</label>--%>
<%--	<a href="/mypage/form" >마이페이지</a>--%>
<%--	<a href="/logout" >로그아웃</a>--%>
<%--</sec:authorize>--%>
<%--	<div>--%>
<%--		<a href="/article/NOTICE/list">공지사항</a>--%>
<%--	</div>--%>


<c:if test="${not empty rMsg}">
	<script type="text/javascript">
		$(document).ready(function () {
			alert('${rMsg}');
		});
	</script>
</c:if>
</body>
</html>

