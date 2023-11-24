<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script type="text/javascript" src="/resources/js/nestable/nestable.js"></script>
<link rel="stylesheet" href="/resources/css/nestable.css">

<script type="text/javascript">

    var updateOutput = function(e) {
        var list   = e.length ? e : $(e.target),
            output = list.data('output');

        if (window.JSON) {
            output.val(window.JSON.stringify(list.nestable('serialize')));//, null, 2));
        } else {
            output.val('JSON browser support required for this demo.');
        }
    };

    $(document).ready(function(){
        // activate Nestable for list 1
        $('#nestable').nestable({
            group: 1,
            maxDepth : 2 // 2dept
        }).on('change', updateOutput);

        // activate Nestable for list 2
        // $('#nestable2').nestable({
        //     group: 1
        // })
        //     .on('change', updateOutput);

        // output initial serialised data
        updateOutput($('#nestable').data('output', $('#nestable-output')));
        // updateOutput($('#nestable2').data('output', $('#nestable2-output')));

        <c:if test="${not empty json}">
        var json = ${json};
        console.log(json);
        if(json){
            $("#nestable-output").val(JSON.stringify(json));
            jsonSetting();
        }

        </c:if>


    });

    // 전체 확장
    function expendAll(){
        $('.dd').nestable('expandAll');
    }
    // 전체 축소
    function collapseAll(){
        $('.dd').nestable('collapseAll');
    }

    // 메뉴 생성
    function createMenu(){
        var time = new Date().getTime() / 1000;
        time = new String(time).replace('.', '');
        $('ol.root').append('' +
            '<li class="dd-item" data-id="'+ time +'" data-update="false">' +
            '   <div class="dd-handle" style="float: left;margin-right: 13px;width: 60%;">메뉴 정보를 입력해주세요.</div>' +
            '   <div style="padding: 10px;">' +
            '       <a href="#none" class="a_btn_type a_btn_color_green" name="'+ time +'" onclick="setMenuInfo('+ time +');">수정</a>' +
            '       <a href="#none" class="a_btn_type a_btn_color_red" name="'+ time +'" onclick="delMenu('+ time +');">삭제</a>' +
            '   </div>' +
            '</li>');
        updateOutput($('#nestable').data('output', $('#nestable-output')));
    }

    // 메뉴 정보 세팅
    function setMenuInfo(id){
        $(".mng_menu_info_div").show();
        $("[name=menu_title]").val('');
        $("[name=menu_link]").val('#');
        $("[name=article_info]").val('');
        $("[name=html_info]").val('');

        $("[name=menu_type]").trigger("chosen:updated");
        $("[name=menu_link_type]").trigger("chosen:updated");

        $(".article_list").hide();
        $(".html_list").hide();

        var item = $('[name='+id+']').parent().parent();


        // 정보를 등록한 메뉴이거나 기존에 등록된 메뉴인 경우
        if($(item).data('update')){
            $('[name=menu_title]').val($(item).data('menu_title'));
            $('[name=menu_type]').val($(item).data('menu_type')).change();
            $('[name=menu_link_type]').val($(item).data('menu_link_type')).change();
            $('[name=menu_link]').val($(item).data('menu_link'));

            // 이벤트 강제 발생
            $('[name=menu_type]').trigger("chosen:updated");
            $('[name=menu_link_type]').trigger("chosen:updated");
            setTimeout(function(){
                console.log($(item));
                $('[name=article_info]').val($(item).data("article_info")).prop("selected",true);
                $('[name=html_info]').val($(item).data("html_info")).prop("selected",true);
            },500)

        }else{
            $('[name=menu_type]').val("MNT_001").prop("selected",true);
            $('[name=menu_link_type]').val("LKT_001").prop("selected",true);
            $('[name=menu_type]').trigger("chosen:updated");
            $('[name=menu_link_type]').trigger("chosen:updated");
        }

        $("#update_btn").attr("href","javascript:saveMenuInfo('"+ id +"')");
    }
    // 메뉴 삭제
    function delMenu(id){

        if(!confirm("해당 메뉴를 삭제하시겠습니까?")){
            return false;
        }

        $("[name="+ id +"]").parent().parent().remove();

        updateOutput($('#nestable').data("output", $('#nestable-output')));
    }
    function fn_cancle(){
        $(".mng_menu_info_div").show();
        $("[name=menu_title]").val('');
        $("[name=menu_link]").val('#');
        $("[name=article_info]").val('');
        $("[name=html_info]").val('');

        $("[name=menu_type]").trigger("chosen:updated");
        $("[name=menu_link_type]").trigger("chosen:updated");

        $(".article_list").hide();
        $(".html_list").hide();

        $("[name=menu_type]").val("MNT_001").prop("selected",true);
        $("[name=menu_link_type]").val("LKT_001").prop("selected",true);
        $("[name=menu_type]").trigger("chosen:updated");
        $("[name=menu_link_type]").trigger("chosen:updated");

        $(".mng_menu_info_div").hide();
    }

    // 메뉴 타입 선택 시 onchange
    function fn_menuType(e){
        var type = $(e).val();

        switch (type){
            case "MNT_001": // 분류
                $(".article_list").hide();
                $(".html_list").hide();
                $("[name=menu_link_type]").attr("readonly",true);
                $("[name=menu_link_type]").attr("disabled",true);
                $("[name=menu_link]").attr("readonly",true);
                break;
            case "MNT_002": // 링크
                $(".article_list").hide();
                $(".html_list").hide();
                $("[name=menu_link_type]").attr("readonly",false);
                $("[name=menu_link_type]").attr("disabled",false);
                $("[name=menu_link]").attr("readonly",false);
                break;
            case "MNT_003": // 게시판
                $(".article_list").show();
                $(".html_list").hide();
                $("[name=menu_link_type]").attr("readonly",false);
                $("[name=menu_link_type]").attr("disabled",false);
                $("[name=menu_link]").attr("readonly",true);
                $("select[name=article_info]").empty();
                $.ajax({
                    url:"/mng/ajax/menu/"+type+"/list",
                    type: "post",
                    dataType: 'json',
                    success:function(res){
                        console.log(res)
                        if(res == null || res.length == 0){
                            alert("게시판 정보가 없습니다.");
                        }else{
                            var content = '';
                            content += '<option value="">선택해주세요.</option>';
                            res.forEach(function(v, i){
                                content += '<option value="'+v.bbs_cd+'">'+v.bbs_nm+'</option>';
                            });
                            $(content).appendTo($("select[name=article_info]"));
                            // $("select[name=article_info]").appendChild(content);
                        }
                    }
                });

                break;
            case "MNT_004": // HTML
                $(".article_list").hide();
                $(".html_list").show();
                $("[name=menu_link_type]").attr("readonly",false);
                $("[name=menu_link_type]").attr("disabled",false);
                $("[name=menu_link]").attr("readonly",true);
                $("select[name=html_info]").empty();
                $.ajax({
                    url:"/mng/ajax/menu/"+type+"/list",
                    type: "post",
                    dataType: 'json',
                    success:function(res){
                        console.log(res)
                        if(res == null || res.length == 0){
                            alert("HTML 정보가 없습니다.");
                        }else{
                            var content = '';
                            content += '<option value="">선택해주세요.</option>';
                            res.forEach(function(v, i){
                                content += '<option value="'+v.html_seq+'">'+v.html_title+'</option>';
                            });
                            $(content).appendTo($("select[name=html_info]"));
                        }
                    }
                });

                break;
        }
    }

    // 메뉴 수정 정보 저장
    function saveMenuInfo(id){

        // 유효성 체크
        if($("[name=menu_title]").val().length == 0){
            alert("메뉴 이름을 입력해주세요.");
            $("[name=menu_title]").focus();
            return false;
        }
        if($("[name=menu_type]").val() == "MNT_002"){
            if($("[name=menu_link]").val().length == 0){
                alert("메뉴 링크를 입력해주세요.");
                $("[name=menu_link]").focus();
                return false;
            }
        }
        if($("[name=menu_type]").val() == "MNT_003"){
            if($("[name=article_info]").val() == ""){
                alert("게시판을 선택해주세요");
                $("[name=article_info]").focus();
                return false;
            }
        }
        if($("[name=menu_type]").val() == "MNT_004"){
            if($("[name=html_info]").val() == ""){
                alert("HTML을 선택해주세요");
                $("[name=html_info]").focus();
                return false;
            }
        }

        var arr = $("form[name=menuInfoForm]").serializeArray();
        var data = {}

        for(var i = 0; i < arr.length; i++){
            data[arr[i].name] = arr[i].value;
        }

        // 사용자 입력정보를 메뉴 정보로 저장
        $("[name="+ id +"]").parent().parent().data("menu_title",data.menu_title);
        $("[name="+ id +"]").parent().siblings(".dd-handle").text(data.menu_title);
        $("[name="+ id +"]").parent().parent().data("menu_type",data.menu_type);
        $("[name="+ id +"]").parent().parent().data("menu_link_type",data.menu_link_type);
        $("[name="+ id +"]").parent().parent().data("menu_link",data.menu_link);
        $("[name="+ id +"]").parent().parent().data("article_info",data.article_info);
        $("[name="+ id +"]").parent().parent().data("html_info",data.html_info);

        $("[name="+ id +"]").parent().parent().data("update", true);

        updateOutput($('#nestable').data("output", $('#nestable-output')));
    }

    function saveMenuAll(){
        var result = true;
        var json = $('#nestable').nestable('serialize');
        json.forEach(function(v, i){
            if(!v.update || v.menu_title == undefined){
                result = false;
                return false;
            }
        });

        if(result){
            // console.log(json);
            // console.log(JSON.stringify(json))
            if(!confirm("메뉴 정보를 저장하시겠습니까?")){
                return false;
            }
            $("[name=menu_json]").val(JSON.stringify(json));
            $("form[name=menuInfoForm]").submit();
            alert("잠시만 기다려주세요.");
        }else{
            alert("메뉴 정보를 다시 확인해주세요.");
        }
    }

    function jsonSetting(){
        var json = $("#nestable-output").val();

        if(json != ""){
            $("ol.root").empty();
            // $("#nestable").empty();
            $("ol.root").append(jsonToMenu(json).children());
        }
    }

    // json 으로 메뉴 생성
    function jsonToMenu(json){
        var ol = $("<ol>").addClass("dd-list");
        var jsonArr = JSON.parse(json);
        $.each(jsonArr, function(i, v){
           // 메뉴
           var li = $("<li" +
               " data-id='"+ v["id"] + "' "+
               " data-menu_title='"+ v["menu_title"] + "' "+
               " data-menu_type='"+ v["menu_type"] + "' "+
               " data-menu_link_type='"+ v["menu_link_type"] + "' "+
               " data-menu_link='"+ v["menu_link"] + "' "+
               " data-article_info='"+ v["article_info"] + "' "+
               " data-html_info='"+ v["html_info"] + "' "+
               " data-update='"+ v["update"] + "' >").addClass("dd-item");

           var li_info = " <div class='dd-handle' style='float: left;margin-right: 13px;width: 60%;'> " + v["menu_title"] + "</div>" +
                " <div style='padding:10px;'> " +
                    "<a href='#none' class='a_btn_type a_btn_color_green' name='" + v["id"] + "' onclick='setMenuInfo("+ v["id"] +");'>수정</a>" +
                    "<a href='#none' class='a_btn_type a_btn_color_red' name='" + v["id"] + "' onClick='delMenu("+ v["id"] +");' style='margin-left: 5px;'>삭제</a>" +
                " </div>";

           li.append(li_info);


           if(!!v["children"]){ // undifined, "" , 0 -> false
               var parentsBtn1 = $("<button data-action='collapse' type='button'' style=''>Collapse</button>");
               var parentsBtn2 = $("<button data-action='expand' type='button' style='display: none;'>Expand</button>");

               li.prepend(parentsBtn2);
               li.prepend(parentsBtn1);
               li.append(jsonToMenu(JSON.stringify(v["children"])));
           }
            ol.append(li);
        });
        return ol;
    }

</script>


<div>
    <menu id="nestable-menu">
        <a href="#" class="a_btn_type a_btn_color_green" onclick="createMenu()" >메뉴 추가</a>
        <a href="#" class="a_btn_type a_btn_color_blue" onclick="expendAll()" >전체 확장</a>
        <a href="#" class="a_btn_type a_btn_color_red" onclick="collapseAll()" >전체 축소</a>
        <a href="#" class="a_btn_type a_btn_color_red" onclick="jsonSetting()" >json TO menu</a>
    </menu>

    <textarea id="nestable-output"></textarea>

    <div class="mng_menu_flex_div">
        <div class="cf nestable-lists">
            <div class="dd" id="nestable">
                <ol class="dd-list root">

                </ol>
            </div>
        </div>
        <div class="mng_menu_config_div">
            <div class="mng_menu_info_div" style="display: none">
                <form:form modelAttribute="menuMngVO" name="menuInfoForm" action="/mng/menu/proc" method="post">
                    <form:hidden path="menu_json"/>
                    <div class="menu_info_flex">
                        <div class="menu_info_subject"><label>메뉴 이름</label></div>
                        <div class="menu_info_content"><input type="text" name="menu_title" maxlength="15"/></div>
                    </div>
                    <div class="menu_info_flex">
                        <div class="menu_info_subject"><label>메뉴 타입</label></div>
                        <div class="menu_info_content">
                            <select name="menu_type" onchange="fn_menuType(this)">
                                <option value="MNT_001">분류</option>
                                <option value="MNT_002">링크</option>
                                <option value="MNT_003">게시판</option>
                                <option value="MNT_004">HTML</option>
                            </select>
                        </div>
                    </div>
                    <div class="menu_info_flex">
                        <div class="menu_info_subject"><label>메뉴 링크 타입</label></div>
                        <div class="menu_info_content">
                            <select name="menu_link_type" readonly disabled>
                                <option value="LKT_001">현재 창</option>
                                <option value="LKT_002">새 창</option>
                            </select>
                        </div>
                    </div>
                    <div class="menu_info_flex">
                        <div class="menu_info_subject"><label>메뉴 링크</label></div>
                        <div class="menu_info_content">
                            <input type="text" name="menu_link" maxlength="50" value="#" readonly/>
                        </div>
                    </div>
                    <div class="menu_info_flex article_list" style="display: none">
                        <div class="menu_info_subject"><label>게시판</label></div>
                        <div class="menu_info_content">
                            <select name="article_info">
                                <option value="N">선택해주세요.</option>
                            </select>
                        </div>
                    </div>
                    <div class="menu_info_flex html_list" style="display: none">
                        <div class="menu_info_subject"><label>HTML</label></div>
                        <div class="menu_info_content">
                            <select name="html_info">
                                <option value="N">선택해주세요.</option>
                            </select>
                        </div>
                    </div>
                    <div class="btn_div">
                        <a id="update_btn" href="#" class="a_btn_type a_btn_color_blue">수정</a>
                        <a id="cancle_btn" href="javascript:fn_cancle()" class="a_btn_type a_btn_color_red">취소</a>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
    <div class="btn_div">
        <a href="javascript:saveMenuAll()" class="a_btn_type a_btn_color_blue" style="font-size: 16px; padding:10px;">전체 저장</a>
    </div>
</div>