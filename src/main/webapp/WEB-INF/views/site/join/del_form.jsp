<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="user_form_div">
    <sec:authorize access="isAuthenticated()">
        <sec:authentication var="USER_DETAIL" property="details"/>
    </sec:authorize>
    <form:form modelAttribute="loginVO" name="mypageForm" action="/mypage/proc" method="post">
        <form:hidden path="user_seq" value="${USER_DETAIL.user_seq}"/>
        <form:hidden path="flag"/>
        <h2>회원탈퇴</h2>
        <h4>정말로 탈퇴하시겠습니까?</h4>

        <table class="user_form_table">
            <tr>
                <th>비밀번호</th>
                <td><form:input type="password" path="user_pwd" placeholder="비밀번호를 입력해주세요." maxlength="30"/></td>
            </tr>
        </table>


<%--        <div>--%>
<%--            <label>비밀번호</label><form:input type="password" path="user_pwd" placeholder="비밀번호를 입력해주세요." maxlength="30"/>--%>
<%--        </div>--%>
        <div style="margin-top: 30px">
            <a href="#" type="button" id="user_del_btn" onclick="fn_delProc('d')" class="a_btn_next">회원탈퇴</a>
            <p id="user_del_msg"></p>
        </div>
    </form:form>
    <script>
        function fn_delProc(flag){

            var user_pwd = $("[name=user_pwd]").val();

            if(user_pwd.length == 0){
                alert("비밀번호를 입력해주세요.");
                return false;
            }

            $("#user_del_btn").hide();
            $("#user_del_msg").html("처리중입니다.. 잠시만 기다려주세요.");

            $.ajax({
                url:'/ajax/mypage/check',
                type: 'get',
                data: {
                    user_seq: $("[name=user_seq]").val(),
                    user_pwd: user_pwd
                },
                success:function(res){
                    console.log(res);
                    if(res.result){
                        if(!confirm("정말로 탈퇴하시겠습니까?")){
                            $("#user_del_btn").show();
                            $("#user_del_msg").html("");
                            return false;
                        }
                        $("[name=flag]").val(flag);
                        $("[name=mypageForm]").submit();
                    }else{
                        alert(res.rMsg);
                        $("#user_del_btn").show();
                        $("#user_del_msg").html("");
                        return false;
                    }
                }
            });

        }
    </script>
</div>