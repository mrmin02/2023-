<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>


<script src="/resources/js/jquery/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" href="/resources/monarch/widgets/datatable_default/1.13.6/jquery.dataTables.css" />
<script src="/resources/monarch/widgets/datatable_default/1.13.6/jquery.dataTables.js"></script>
<div>
    <script type="text/javascript">
        $(document).ready(function () {
            var table = $('#listTable').DataTable({
                <%-- 옵션넣어서 쓰면 됩니다. --%>
            });
            $('.dataTables_filter input').attr("placeholder", "검색어를 입력하세요.");
        });
    </script>
    <form:form modelAttribute="bbsMngVO" action="/mng/bbs/form" method="post" name="bbsListForm">
        <form:hidden path="bbs_info_seq" />
        <form:hidden path="flag" value="u" />
    </form:form>
    <div class="panel">
        <div class="panel-body">
            <h3 class="title-hero">
                게시판 목록
            </h3>
            <div class="example-box-wrapper">
                <div class="size-md">
                    <a class="a_btn_small" href="/mng/bbs/form" style="float: right; margin: 0 10px 10px 0;">게시판 정보 등록</a>
                </div>
                <div class="example-box-wrapper">
                    <table id="listTable" class="display" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th>No</th>
                        <th>게시판 코드</th>
                        <th>게시판 이름</th>
                        <th>사용 여부</th>
                        <th>삭제 여부</th>
                        <th>게시판 타입</th>
                        <th>공지사항 사용 여부</th>
                        <th>생성일</th>
                        <th>수정일</th>
                        <th>바로가기</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="list" varStatus="idx">
                        <tr style="cursor: pointer;" onclick="javascript:fn_bbsForm('${list.bbs_info_seq}')">
                            <td>${idx.count}</td>
                            <td>${list.bbs_cd}</td>
                            <td>${list.bbs_nm}</td>
                            <td>${list.bbs_use_yn eq 'Y' ? '사용중' : '미사용'}</td>
                            <td>${list.bbs_del_yn eq 'Y' ? '삭제' : '-'}</td>
                            <td>${list.bbs_type eq 'BBS_001' ? '리스트' : '추가예정'}</td>
                            <td>${list.notice_use_yn eq 'Y' ? '사용' : '미사용'}</td>
                            <td>${list.inpt_date}</td>
                            <td>${list.upd_date}</td>
                            <td><button type="button" onclick="event.cancelBubble=true;location.href='/mng/article/${list.bbs_cd}/list'" >게시글</button></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <script>
        function fn_bbsForm(bbs_info_seq) {
            $("[name=bbs_info_seq]").val(bbs_info_seq);
            $("[name=bbsListForm]").submit();
        }
    </script>
</div>