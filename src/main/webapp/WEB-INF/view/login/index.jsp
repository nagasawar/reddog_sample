<%@page import="reddog_sample.util.helper.Html"%>
<%@page pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">
<tiles:put name="title" value="ログイン" />
<tiles:put name="content" type="string">

<div class="well">

<s:form method="POST" styleClass="form-horizontal">

    <fieldset>
        <legend>ログイン</legend>
        <html:errors/>

        <%-- ログインID --%>
        <div class="form-group">
            <label for="txt-loginId" class="col col-xs-12 col-sm-3 col-md-2 control-label">ログインID</label>
            <div class="col col-xs-12 col-sm-6 col-md-7 <%= Html.bsErrCls("loginId") %>">
                <html:text property="loginId" styleId="txt-loginId" styleClass="form-control rd-initFocus"/>
            </div>
        </div>

        <%-- パスワード --%>
        <div class="form-group">
            <label for="txt-password" class="col col-xs-12 col-sm-3 col-md-2 control-label">パスワード</label>
            <div class="col col-xs-12 col-sm-6 col-md-7 <%= Html.bsErrCls("password") %>">
                <html:password property="password" styleId="txt-password" styleClass="form-control"/>
            </div>
        </div>

        <%-- サインイン ボタン --%>
        <div class="form-group">
            <div class="col col-xs-9 col-xs-offset-3 col-sm-8 col-sm-offset-4 col-md-9 col-md-offset-3">
                <s:submit property="login" value="サインイン" styleClass="btn btn-primary"/>
            </div>
        </div>
    </fieldset>

    <%-- ログイン後のurl --%>
    <html:hidden property="url"/>

</s:form>

</div>

<script type="text/javascript">
$(function() {
    $('#txt-loginId').attr('placeholder', 'ログインID');
    $('#txt-password').attr('placeholder', 'パスワード');
});
</script>

</tiles:put>
</tiles:insert>
