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

  <form:form modelAttribute="htmlMngVO" action="/mng/html/form" method="post" name="htmlListForm">
    <form:hidden path="flag" value="u" />
  </form:form>
  <div class="panel">
    <div class="panel-body">
      <h3 class="title-hero">
        HTML 목록
      </h3>
      <div class="example-box-wrapper">
        <div class="size-md">
          <a class="a_btn_small" href="javascript:fn_HtmlForm('c')" style="float: right; margin: 0 10px 10px 0;">HTML 작성</a>
        </div>
        <table id="listTable" class="display"
               cellspacing="0" width="100%">
          <colgroup>
            <col width="10%">
            <col width="*">
            <col width="15%">
            <col width="15%">
            <col width="15%">
          </colgroup>
          <thead>
          <tr>
            <th>No</th>
            <th>제목</th>
            <th>삭제 여부</th>
            <th>등록일</th>
            <th>수정일</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${list}" var="list" varStatus="idx">
            <tr style="cursor: pointer;" onclick="javascript:location.href='/mng/html/detail/${list.html_seq}'">
              <td>${idx.count}</td>
              <td>${list.html_title}</td>
              <td>${list.del_yn eq 'Y' ? '삭제' : '-'}</td>
              <td>${list.inpt_date}</td>
              <td>${list.upd_date}</td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
  <script>
    function fn_HtmlForm(flag) {
      $("[name=flag]").val(flag);
      $("[name=htmlListForm]").submit();
    }
  </script>
</div>