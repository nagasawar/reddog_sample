<%@page pageEncoding="UTF-8"%>
<%@page import="reddog_sample.util.helper.Html"%>
<%@page import="reddog_sample.util.helper.JsValidBuilder"%>
<% JsValidBuilder.setRdForm(request, "user_inputForm"); %>

<%-- 新規作成、編集の入力部分 --%>

<%-- ログインID --%>
<div class="form-group">
    <label for="txt-loginId" class="col col-xs-12 col-sm-4 col-md-3 control-label">ログインID</label>
    <div class="col col-xs-12 col-sm-6 col-md-7 <%= Html.bsErrCls("loginId") %>">
        <rdf:text property="loginId"
                  styleId="txt-loginId"
                  styleClass="form-control rd-initFocus"
                  jscheck="true"
                  />
        <rdf:errors property="loginId"/>
    </div>
</div>

<%-- パスワード --%>
<div class="form-group">
    <label for="txt-password" class="col col-xs-12 col-sm-4 col-md-3 control-label">パスワード</label>
    <div class="col col-xs-12 col-sm-6 col-md-7 <%= Html.bsErrCls("password") %>">
        <rdf:password property="password"
                      styleId="txt-password"
                      styleClass="form-control"
                      jscheck="true"
                      />
        <rdf:errors property="password"/>
    </div>
</div>

<%-- ユーザ名 --%>
<div class="form-group">
    <label for="txt-userName" class="col col-xs-12 col-sm-4 col-md-3 control-label">ユーザ名</label>
    <div class="col col-xs-12 col-sm-6 col-md-7 <%= Html.bsErrCls("userName") %>">
        <rdf:text property="userName"
                  styleId="txt-userName"
                  styleClass="form-control"
                  jscheck="true"
                  />
        <rdf:errors property="userName"/>
    </div>
</div>

<%-- ユーザ権限 --%>
<div class="form-group">
    <label for="sel-userRole" class="col col-xs-12 col-sm-4 col-md-3 control-label">ユーザ権限</label>
    <div class="col col-xs-12 col-sm-6 col-md-7 <%= Html.bsErrCls("userRoleId") %>">
         <rdf:select
           property       = "userRoleId"
           styleId        = "sel-userRole"
           styleClass     = "form-control"
           optionProperty = "rdUserRoles"
           optionLabel    = "userRoleName"
           optionValue    = "userRoleId"
           jscheck        = "true"
           />
        <rdf:errors property="userRoleId"/>
    </div>
</div>

<script type="text/javascript">
$(function() {
    // placeholderを追加
    $('#txt-loginId').attr('placeholder', 'loginId');
    $('#txt-password').attr('placeholder', 'password');
    $('#txt-userName').attr('placeholder', '山田　太郎');

    // チェックボックスにhiddenを付ける
    $('form').reddog('addCheckboxHidden');
});
</script>
