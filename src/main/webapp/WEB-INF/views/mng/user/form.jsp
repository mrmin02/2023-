<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script type="text/javascript" src="/resources/monarch/widgets/parsley/parsley.js"/>

<script type="text/javascript">
    $(document).ready(function() {
        window.ParsleyConfig = {
            excluded: 'input[type=button], input[type=submit], input[type=reset]',
            inputs: 'input, textarea, select, input[type=hidden], :hidden'
        };
        $('[name=userForm]').parsley(); // vo 에 값이 있으면 해당하는 태그에 값 대입

        // 비밀번호 수정 시 ...
        $('[name=user_pwd]').on('keydown keyup keypress', function(e){
            $('[name=user_pwd]').attr('data-parsley-required', $('[name=user_pwd]').val() != '' ? 'true' : 'false');
        });
    });
</script>
<div class="mng_bbs_form">
    <form:form modelAttribute="userMngVO" name="userForm" action="/mng/user/proc" method="post" data-parsley-validate="true">
        <form:hidden path="user_seq" value="${userMngVO.user_seq}"/>
        <form:hidden path="flag" value="${empty userMngVO.user_seq ? 'c' : 'u'}"/>


        <h3 class="mng_bbs_info">기본정보</h3>
        <div class="mng_info_div">
            <div class="mng_info_flex">
                <div class="info_title">아이디</div>
                <div class="info_content">
                    <c:choose>
                        <c:when test="${empty userMngVO.user_seq}">
                            <form:input type="text" path="user_id" placeholder="아이디를 입력해주세요." maxlength="15"
                                        onkeyup="chekc_id_false()" onkeypress="chekc_id_false()" cssClass="info_input" required="true"/>
                            <a href="javascript:checkId()" class="a_btn_small">중복체크</a>
                            <label id="check_msg"></label>
                        </c:when>
                        <c:when test="${not empty userMngVO.user_seq}">
                            <c:out value="${userMngVO.user_id}"/>
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </div>
        <div class="mng_info_div">
            <div class="mng_info_flex">
                <div class="info_title">비밀번호</div>
                <div class="info_content">
                    <form:input path="user_pwd" type="password"  maxlength="20" placeholder="비밀번호를 입력해주세요." cssClass="data-parsley-required info_input" />
                </div>
            </div>
        </div>
        <div class="mng_info_div">
            <div class="mng_info_flex">
                <div class="info_title">이름</div>
                <div class="info_content">
                    <form:input path="user_name" type="text"  maxlength="20" placeholder="성함을 입력해주세요." cssClass="data-parsley-required info_input" required="true"/>
                </div>
            </div>
        </div>
        <div class="mng_info_div">
            <div class="mng_info_flex">
                <div class="info_title">권한</div>
                <div class="info_content">
                    <form:select path="user_auth" cssClass="info_select">
                        <form:option value="ROLE_USER">사용자</form:option>
                        <form:option value="ROLE_ADMIN">관리자</form:option>
                    </form:select>
                </div>
            </div>
        </div>
        <div class="mng_info_div">
            <div class="mng_info_flex">
                <div class="info_title">탈퇴 여부</div>
                <div class="info_content">
                    <label><input name="del_yn" type="radio" value="N" ${userMngVO.del_yn eq 'N' ? 'checked' : ''} required="true">사용중</label>
                    <label><input name="del_yn" type="radio" value="Y" ${userMngVO.del_yn eq 'Y' ? 'checked' : ''} required="true">탈퇴</label>
                </div>
            </div>
        </div>

        <c:if test="${not empty userMngVO.user_seq}">
            <div class="mng_info_div">
                <div class="mng_info_flex">
                    <div class="info_title">가입일</div>
                    <div class="info_content">
                        <c:out value="${userMngVO.inpt_date}"/>
                    </div>
                </div>
            </div>
            <div class="mng_info_div">
                <div class="mng_info_flex">
                    <div class="info_title">수정일</div>
                    <div class="info_content">
                        <c:out value="${userMngVO.upd_date}"/>
                    </div>
                </div>
            </div>
        </c:if>
    </form:form>

    <div class="btn_div">
        <a href="/mng/user/list" class="a_btn_green">목록</a>
        <c:choose>
            <c:when test="${empty userMngVO.user_seq}">
                <a href="javascript:fn_proc('c');" class="a_btn_blue">확인</a>
            </c:when>
            <c:when test="${not empty userMngVO.user_seq}">
                <a href="javascript:fn_proc('u');" class="a_btn_blue">수정</a>
                <a href="javascript:fn_proc('d');" class="a_btn_red">삭제</a>
            </c:when>
        </c:choose>
    </div>

    <script>

        function fn_proc(flag){
            var check_state = false;

            if(flag != 'd'){
                // 삭제가 아닐 경우, 유효성 체크
                if(flag == 'c'){
                    if($("[name=user_id]").length == 0){
                        alert("아이디를 입력해주세요.");
                        return false;
                    }
                    // 아이디 중복체크 검사
                    if(!check_state){
                        alert("아이디 중복검사를 진행해주세요.");
                        return false;
                    }
                    if($("[name=user_pwd]").length == 0){
                        alert("비밀번호를 입력해주세요.");
                        return false;
                    }
                }
            }


            var msg = flag == 'c' ? "생성" : flag == 'u' ? "수정" : "삭제";
            var confirm_msg = "회원정보를 " + msg + " 하시겠습니까?";

            if(!confirm(confirm_msg)){
                return false;
            }

            $("[name=flag]").val(flag);
            $("form[name=userForm]").submit();
        }

        /**
         * 아이디 입력 시, 중복체크 상태 false 로 변경
         */
        function chekc_id_false(){
            check_state = false;
            $("#check_msg").html("");
            $("#check_msg").css("display","inline");
        }

        /**
         * 아이디 중복검사
         * @returns {boolean}
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
                    $("#check_msg").css("display","block");
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
    </script>
</div>