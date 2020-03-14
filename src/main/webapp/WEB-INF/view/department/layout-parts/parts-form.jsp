<%@page pageEncoding="UTF-8"%>
<%@page import="reddog_sample.util.helper.Html"%>
<%@page import="reddog_sample.util.helper.JsValidBuilder"%>
<% JsValidBuilder.setRdForm(request, "department_inputForm"); %>

<%-- 新規作成、編集の入力部分 --%>

<%-- 所属ID --%>
<div class="form-group">
    <label for="txt-departmentId" class="col col-xs-12 col-sm-4 col-md-3 control-label">所属ID</label>
    <div class="col col-xs-12 col-sm-6 col-md-7 <%= Html.bsErrCls("departmentId") %>">

        <%-- 新規作成の場合はテキストボックス --%>
        <c:if test="${addNewMode}">
            <rdf:text property="departmentId"
                      styleId="txt-departmentId"
                      styleClass="form-control rd-initFocus"
                      jscheck="true"
                      />
            <rdf:errors property="departmentId"/>
        </c:if>

        <c:if test="${!addNewMode}">
            <p class="form-control-static">${f:h(departmentId)}</p>
            <html:hidden property="departmentId" styleId="txt-departmentId"/>
        </c:if>
    </div>
</div>

<%-- 所属名 --%>
<div class="form-group">
    <label for="departmentName" class="col col-xs-12 col-sm-4 col-md-3 control-label">所属名</label>
    <div class="col col-xs-12 col-sm-6 col-md-7 <%= Html.bsErrCls("departmentName") %>">
        <rdf:text property="departmentName"
                  styleId="txt-departmentName"
                  styleClass="form-control"
                  jscheck="true"
                  />
        <rdf:errors property="departmentName"/>
    </div>
</div>

<script type="text/javascript">
$(function() {
    // placeholderを追加
    $('#txt-departmentId').attr('placeholder',   '001001');
    $('#txt-departmentName').attr('placeholder', '営業部');
});
</script>
