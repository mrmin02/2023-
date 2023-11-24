<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%--<script type="text/javascript" src="/resources/monarch/widgets/datatable/datatable.js"></script>--%>
<%--<script type="text/javascript" src="/resources/monarch/widgets/datatable/datatable-bootstrap.js"></script>--%>
<%--<script type="text/javascript" src="/resources/monarch/widgets/datatable/datatable-tabletools.js"></script>--%>

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

    <div class="panel">
        <div class="panel-body">
            <h3 class="title-hero">
                회원 목록
            </h3>
            <div class="example-box-wrapper">
                <div class="size-md">
                    <a class="a_btn_small" href="/mng/user/form" style="float: right; margin: 0 10px 10px 0;">회원 등록</a>
                </div>
                <table id="listTable" class="display"
                       cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th>No</th>
                        <th>아이디</th>
                        <th>이름</th>
                        <th>권한</th>
                        <th>탈퇴여부</th>
                        <th>가입일</th>
                        <th>수정일</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="list" varStatus="idx">
                        <tr style="cursor: pointer;" onclick="javascript:location.href='/mng/user/detail/${list.user_seq}'">
                            <td>${idx.count}</td>
                            <td>${list.user_id}</td>
                            <td>${list.user_name}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${list.user_auth eq 'ROLE_ADMIN'}">
                                        관리자
                                    </c:when>
                                    <c:when test="${list.user_auth eq 'ROLE_USER'}">
                                        사용자
                                    </c:when>
                                </c:choose>
                            </td>
                            <td>${list.del_yn eq 'Y' ? '탈퇴' : '-'}</td>
                            <td>${list.inpt_date}</td>
                            <td>${list.upd_date}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>