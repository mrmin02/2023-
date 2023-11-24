<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="policy_div">

    <textarea readonly>정책 주저리 주저리..</textarea>
    <label><input type="checkbox" class="policy_agree"/>위의 내용에 동의합니다.</label>
    <textarea readonly>정책 주저리 주저리..</textarea>
    <label><input type="checkbox" class="policy_agree"/>위의 내용에 동의합니다.</label>
    <textarea readonly>정책 주저리 주저리..</textarea>
    <label><input type="checkbox" class="policy_agree"/>위의 내용에 동의합니다.</label>

    <a href="javascript:policy_check()" type="button" class="a_btn_next" >다음으로</a>



    <script>
        /**
         * 정책 동의 체크 및 회원가입 form으로 이동
         * @returns {boolean}
         */
        function policy_check(){

            // 정책 모두 동의
            if(!($("input.policy_agree:checked").length == $("input.policy_agree").length)){
                alert("모든 정책 미동의 시, 회원가입이 불가능합니다.");
                return false;
            }

            // 회원가입 form 으로 이동
            location.href = "/join/form";
        }
    </script>
</div>