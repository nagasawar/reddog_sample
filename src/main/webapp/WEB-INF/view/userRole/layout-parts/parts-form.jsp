<%@page pageEncoding="UTF-8"%>
<%@page import="reddog_sample.util.helper.Html"%>
<%@page import="reddog_sample.util.helper.JsValidBuilder"%>
<% JsValidBuilder.setRdForm(request, "userRole_inputForm"); %>

<%-- 新規作成、編集の入力部分 --%>

<%-- ユーザ権限ID --%>
<div class="form-group">
    <label for="txt-userRoleId" class="col col-xs-12 col-sm-4 col-md-3 control-label">ユーザ権限ID</label>
    <div class="col col-xs-12 col-sm-6 col-md-7 <%= Html.bsErrCls("userRoleId") %>">

        <%-- 新規作成の場合はテキストボックス --%>
        <c:if test="${addNewMode}">
            <rdf:text property="userRoleId"
                      styleId="txt-userRoleId"
                      styleClass="form-control rd-initFocus"
                      jscheck="true"
                      />
            <rdf:errors property="userRoleId"/>
        </c:if>

        <c:if test="${!addNewMode}">
            <p class="form-control-static">${f:h(userRoleId)}</p>
            <html:hidden property="userRoleId" styleId="txt-userRoleId"/>
        </c:if>
    </div>
</div>

<%-- ユーザ権限名 --%>
<div class="form-group">
    <label for="userRoleName" class="col col-xs-12 col-sm-4 col-md-3 control-label">ユーザ権限名</label>
    <div class="col col-xs-12 col-sm-6 col-md-7 <%= Html.bsErrCls("userRoleName") %>">
        <rdf:text property="userRoleName"
                  styleId="txt-userRoleName"
                  styleClass="form-control"
                  jscheck="true"
                  />
        <rdf:errors property="userRoleName"/>
    </div>
</div>

<script type="text/javascript">
$(function() {
    // placeholderを追加
    $('#txt-userRoleId').attr('placeholder',   '5');
    $('#txt-userRoleName').attr('placeholder', 'グループ管理者');
});
</script>
