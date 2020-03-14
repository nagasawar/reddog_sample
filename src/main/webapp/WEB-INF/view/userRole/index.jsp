<%@page pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">
<tiles:put name="title" value="ユーザ権限検索" />
<tiles:put name="content" type="string">

<ul class="breadcrumb">
      <li><s:link href="/">TOP</s:link>
      <li class="active">ユーザ権限検索</li>
</ul>

<s:form method="POST" styleClass="form-horizontal">

<fieldset>
    <legend>ユーザ権限検索</legend>

    <div align="right" style="margin-bottom: 5px;">
        <s:link href="addNew" styleId="lnk-addNew" styleClass="btn btn-success">F9 新規作成</s:link>
        <s:link href="#" styleId="lnk-reload" styleClass="btn btn-success">F5 画面更新</s:link>
    </div>

    <%-- 検索結果一覧 --%>
    <div style="margin-bottom: 20px;">
        <table id="dataTable" style="display: none; width: 100%;" class="table rd-dataTableStyle">
            <thead>
                <tr>
                    <th>ユーザ権限ID</th>
                    <th>ユーザ権限名</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="o" items="${rdUserRoles}">
                <tr>
                    <td class="userRoleId">
                        ${f:h(o.userRoleId)}
                        <input type="hidden" class="hid-dataTable-keyId" value="<c:out value="${o.userRoleId}" />" disabled="disabled" />
                    </td>
                    <td class="userRoleName">${f:h(o.userRoleName)}</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

</fieldset>

</s:form>

<rd:dtAjaxEditor-list
    urlEdit     = "/userRole/edit"
    urlAddNew   = "/userRole/addNew"
    urlDelete   = "/userRole/delete"
    dataTableId = "dataTable"
    addNewId    = "lnk-addNew"
/>

<%-- ------------------------------------------------- --%>
<script src="<%= request.getContextPath() %>/asset/js/plugin/jquery.reddog.functionKeyEvent.js"></script>

<script type="text/javascript">

$(function() {

    // F9 新規ボタン
    $(document).functionKeyEvent({
        fkname: RD_FK9,
        event: function() {
            $('#lnk-addNew').trigger('click');
        }
    });

    // F5 画面更新ボタン
    $('#lnk-reload').click(function() {
        location.reload();
        return false;
    });

});
</script>

</tiles:put>
</tiles:insert>
