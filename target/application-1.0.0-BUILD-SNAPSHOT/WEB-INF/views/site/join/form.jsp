<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="user_form_div">
    <sec:authorize access="isAuthenticated()">
        <sec:authentication var="USER_DETAIL" property="details"/>
    </sec:authorize>

    <form:form modelAttribute="loginVO" name="loginForm" action="/join/proc" method="post">

        <table class="user_form_table">
            <tr>
                <th>아이디</th>
                <td><form:input type="text" path="user_id" placeholder="아이디를 입력해주세요." maxlength="15"
                                onkeyup="chekc_id_false()" onkeypress="chekc_id_false()"/><p id="check_msg"></p></td>
                <td><a href="javascript:checkId()" type="button" class="a_btn_small">중복체크</a></td>
            </tr>
            <tr>
                <th>비밀번호</th>
                <td><form:input type="password" path="user_pwd" placeholder="비밀번호를 입력해주세요." maxlength="30"/></td>
            </tr>
            <tr>
                <th>이름</th>
                <td><form:input type="text" path="user_name" placeholder="이름을 입력해주세요." maxlength="15"/></td>
            </tr>
        </table>


    <%--    <div>--%>
    <%--        <label>아이디</label>--%>
    <%--        <form:input type="text" path="user_id" placeholder="아이디를 입력해주세요." maxlength="15"--%>
    <%--                    onkeyup="chekc_id_false()" onkeypress="chekc_id_false()"/>--%>
    <%--        <button type="button" onclick="checkId()">중복체크</button>--%>
    <%--        <p id="check_msg"></p>--%>
    <%--    </div>--%>
    <%--    <div>--%>
    <%--        <label>비밀번호</label><form:input type="password" path="user_pwd" placeholder="비밀번호를 입력해주세요." maxlength="30"/>--%>
    <%--    </div>--%>
    <%--    <div>--%>
    <%--        <label>이름</label><form:input type="text" path="user_name" placeholder="이름을 입력해주세요." maxlength="15"/>--%>
    <%--    </div>--%>

        <div class="btn_div">
            <a href="#" type="button" onclick="joinProc()" class="a_btn_next">회원가입</a>
        </div>
    </form:form>
    <script>
        var check_state = false;

        /**
         * 아이디 입력 시, 중복체크 상태 false 로 변경
         */
        function chekc_id_false(){
            check_state = false;
            $("#check_msg").html("");
        }
        /**
         * 아이디 중복체크
         */
        function checkId(){

            var user_id = $("[name=user_id]").val();
            if(user_id.length == 0){
                alert("아이디를 입력해주세요.");
                return false;
            }

            $.ajax({
                url:'/ajax/join/check',
                type: 'get',
                data: {user_id: user_id},
                success:function(res){
                    console.log(res);
                    if(res.result){
                        check_state = true;
                        $("#check_msg").html("중복체크 완료!");
                        $("#check_msg").css("color","blue");
                    }else{
                        check_state = false;
                        $("#check_msg").html("이미 존재하는 아이디입니다.");
                        $("#check_msg").css("color","red");
                    }
                }
            });
        }
        /**
         * 회원가입 Process
         */
        function joinProc(){
            /**
             * 유효성 검사
             */
            // 아이디 검사
            if($("[name=user_id]").val().length == 0 ){
                alert("아이디를 입력해주세요.");
                return false;
            }
            // 아이디 중복체크 검사
            if(!check_state){
                alert("아이디 중복검사를 진행해주세요.");
                return false;
            }
            // 비밀번호 검사
            if($("[name=user_pwd]").val().length == 0 ){
                alert("비밀번호를 입력해주세요.");
                return false;
            }
            // 이름 검사
            if($("[name=user_name]").val().length == 0 ){
                alert("성함을 입력해주세요.");
                return false;
            }

            if(!confirm("해당 정보로 회원가입을 진행하시겠습니까?")){
                return false;
            }

            $("form[name=loginForm]").submit();
        }
    </script>
</div>