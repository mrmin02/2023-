<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%--<script type="text/javascript" src="/resources/js/jquery/jquery-3.7.1.min.js"></script>--%>
<script type="text/javascript" src="/resources/monarch/widgets/parsley/parsley.js"/>

<script type="text/javascript">
    $(document).ready(function() {
        window.ParsleyConfig = {
            excluded: 'input[type=button], input[type=submit], input[type=reset]',
            inputs: 'input, textarea, select, input[type=hidden], :hidden'
        };
        $('#bbsForm').parsley(); // vo 에 값이 있으면 해당하는 태그에 값 대입

        if(${not empty bbsMngVO.bbs_info_seq? true : false}){
            $("#check_btn").prop("disabled", true);
        }
    });
</script>

<div class="mng_bbs_form">
    <form:form modelAttribute="bbsMngVO" id="bbsForm" name="bbsForm" action="/mng/bbs/proc" method="post" data-parsley-validate="true">
        <form:hidden path="bbs_info_seq" value="${bbsMngVO.bbs_info_seq}"/>
        <form:hidden path="flag" value="${empty bbsMngVO.bbs_info_seq ? 'c' : 'u'}"/>
        <form:hidden path="bbs_cd_cg" value="N"/>

        <h3 class="mng_bbs_info">기본정보</h3>
        <div class="mng_info_div">
            <div class="mng_info_flex">
                <div class="info_title">게시판 코드</div>
                <div class="info_content">
                    <form:input type="text" path="bbs_cd" placeholder="게시판 코드를 입력해주세요." maxlength="15"
                                onkeyup="check_cd_false()" onkeypress="check_cd_false()" cssClass="info_input" required="true"/>
                    <a href="javascript:checkBbsCd()" id="check_btn" class="a_btn_small">중복체크</a>
                    <label id="check_msg"></label>
                </div>
            </div>
            <div class="mng_info_flex">
                <div class="info_title">게시판 이름</div>
                <div class="info_content">
                    <form:input path="bbs_nm" type="text"  maxlength="20" placeholder="게시판 이름을 입력해주세요." cssClass="data-parsley-required info_input" required="true"/>
                </div>
            </div>
            <div class="mng_info_flex">
                <div class="info_title">게시판 설명</div>
                <div class="info_content">
                    <form:input path="bbs_remark" type="text"  maxlength="50" placeholder="게시판 설명을 입력해주세요." cssClass="data-parsley-required info_input" required="true"/>
                </div>
            </div>
            <div class="mng_info_flex">
                <div class="info_title">사용 여부</div>
                <div class="info_content">
                    <label><input name="bbs_use_yn" type="radio" value="Y" ${bbsMngVO.bbs_use_yn eq 'Y' ? 'checked' : ''} required="true"/>사용중</label>
                    <label><input name="bbs_use_yn" type="radio" value="N" ${bbsMngVO.bbs_use_yn eq 'N' ? 'checked' : ''} required="true"/>미사용</label>
                </div>
            </div>
            <div class="mng_info_flex">
                <div class="info_title">삭제 여부</div>
                <div class="info_content">
                    <label><input name="bbs_del_yn" type="radio" value="N" ${bbsMngVO.bbs_del_yn eq 'N' ? 'checked' : ''} required="true"/>사용</label>
                    <label><input name="bbs_del_yn" type="radio" value="Y" ${bbsMngVO.bbs_del_yn eq 'Y' ? 'checked' : ''} required="true"/>삭제</label>
                </div>
            </div>
        </div>

        <h3 class="mng_bbs_info">상세정보</h3>
        <div class="mng_info_div">
            <div class="mng_info_flex">
                <div class="info_title">게시판 타입</div>
                <div class="info_content">
                    <form:select path="bbs_type" required="true" cssClass="info_select">
                        <form:option value="BBS_001">리스트</form:option>
                        <form:option value="BBS_002">추가예정</form:option>
                    </form:select>
                </div>
            </div>
            <div class="mng_info_flex">
                <div class="info_title">공지사항 사용 여부</div>
                <div class="info_content">
                    <label><input name="notice_use_yn" type="radio" value="Y" ${bbsMngVO.notice_use_yn eq 'Y' ? 'checked' : ''} required="true"/>사용</label>
                    <label><input name="notice_use_yn" type="radio" value="N" ${bbsMngVO.notice_use_yn eq 'N' ? 'checked' : ''} required="true"/>미사용</label>
                </div>
            </div>
            <div class="mng_info_flex">
                <div class="info_title">첨부파일 사용여부</div>
                <div class="info_content">
                    <label><input name="attach_file_use_yn" type="radio" value="Y" ${bbsMngVO.attach_file_use_yn eq 'Y' ? 'checked' : ''} required="true">사용</label>
                    <label><input name="attach_file_use_yn" type="radio" value="N" ${bbsMngVO.attach_file_use_yn eq 'N' ? 'checked' : ''} required="true">미사용</label>
                </div>
            </div>
            <div class="mng_info_flex">
                <div class="info_title">첨부파일 개수</div>
                <div class="info_content">
                    <form:input path="file_cnt" cssClass="info_input" type="text" value="${empty bbsMngVO.file_cnt ? '3' : bbsMngVO.file_cnt}" />
                </div>
            </div>
            <div class="mng_info_flex">
                <div class="info_title">파일 확장자 <span style="display: block;">( \\로 구분 )</span></div>
                <div class="info_content">
                    <form:input path="file_ext" cssClass="info_input" type="text" maxlength="50"/>
                </div>
            </div>
            <div class="mng_info_flex">
                <div class="info_title">개시판 목록 수</div>
                <div class="info_content">
                    <form:input path="bbs_dft_list_cnt" cssClass="info_input" type="text" value="${empty bbsMngVO.bbs_dft_list_cnt ? '10' : bbsMngVO.bbs_dft_list_cnt}" />
                </div>
            </div>
            <div class="mng_info_flex">
                <div class="info_title">페이지 수</div>
                <div class="info_content">
                    <form:input path="bbs_page_cnt" cssClass="info_input" type="text" value="${empty bbsMngVO.bbs_page_cnt ? '5' : bbsMngVO.bbs_page_cnt}" />
                </div>
            </div>
            <c:if test="${not empty bbsMngVO.bbs_info_seq}">
                <div class="mng_info_flex">
                    <div class="info_title">생성일</div>
                    <div class="info_content">
                        <c:out value="${bbsMngVO.inpt_date}" />
                    </div>
                </div>
                <div class="mng_info_flex">
                    <div class="info_title">수정일</div>
                    <div class="info_content">
                        <c:out value="${bbsMngVO.upd_date}"/>
                    </div>
                </div>
            </c:if>
        </div>

    </form:form>
    <div class="btn_div">
        <a href="/mng/bbs/list" class="a_btn_green">목록</a>
        <c:choose>
            <c:when test="${empty bbsMngVO.bbs_info_seq}">
                <a href="javascript:fn_proc('c');" class="a_btn_blue">확인</a>
            </c:when>
            <c:when test="${not empty bbsMngVO.bbs_info_seq}">
                <a href="javascript:fn_proc('u');" class="a_btn_blue">수정</a>
                <a href="javascript:fn_proc('d');" class="a_btn_red">삭제</a>
            </c:when>
        </c:choose>
    </div>

    <script>
        var check_state = ${not empty bbsMngVO.bbs_info_seq? true : false};

        function fn_proc(flag){


            if(flag != 'd'){
                // 삭제가 아닐 경우, 유효성 체크
                if(flag == 'c'){
                    if($("[name= bbs_cd]").length == 0){
                        alert("게시판 코드를 입력해주세요.");
                        return false;
                    }
                    // 아이디 중복체크 검사
                    if(!check_state){
                        alert("게시판 코드 중복검사를 진행해주세요.");
                        return false;
                    }
                }
            }


            var msg = flag == 'c' ? "생성" : flag == 'u' ? "수정" : "삭제";
            var confirm_msg = "게시판을 " + msg + " 하시겠습니까?";

            if(!confirm(confirm_msg)){
                return false;
            }

            $("[name=flag]").val(flag);
            $("form[name=bbsForm]").submit();
        }

        /**
         * 게시판 코드 입력 시, 중복체크 상태 false 로 변경
         */
        function check_cd_false(){
            if(check_state){
                $("#check_btn").prop("disabled", false);
                $("[name=bbs_cd_cg]").val("Y");
            }
            check_state = false;
            $("#check_msg").html("");
            $("#check_msg").css("display","inline");
        }

        /**
         * 게시판 코드 중복검사
         * @returns {boolean}
         */
        function checkBbsCd(){

            var bbs_cd = $("[name=bbs_cd]").val();
            if(bbs_cd.length == 0){
                alert("게시판 코드를 입력해주세요.");
                return false;
            }

            $.ajax({
                url:'/mng/ajax/bbs/check',
                type: 'get',
                data: {bbs_cd: bbs_cd},
                success:function(res){
                    console.log(res);
                    $("#check_msg").css("display","block");
                    if(res.result){
                        check_state = true;
                        $("#check_msg").html("사용 가능한 게시판 코드입니다.");
                        $("#check_msg").css("color","blue");
                    }else{
                        check_state = false;
                        $("#check_msg").html("이미 존재하는 게시판 코드입니다.");
                        $("#check_msg").css("color","red");
                    }
                }
            });
        }
    </script>
</div>
