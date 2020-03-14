<%@page pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">
<tiles:put name="title" value="ユーザ検索" />
<tiles:put name="content" type="string">

<ul class="breadcrumb">
      <li><s:link href="/">TOP</s:link>
      <li class="active">ユーザ検索</li>
</ul>

<s:form method="POST" styleClass="form-horizontal">

<fieldset>
    <legend>ユーザ検索</legend>

    <div align="right" style="margin-bottom: 5px;">
        <s:link href="addNew" styleId="lnk-addNew" styleClass="btn btn-success">F9 新規作成</s:link>
        <s:link href="#" styleId="lnk-reload" styleClass="btn btn-success">F5 画面更新</s:link>
    </div>

    <%-- 検索条件 --%>
    <bs:panel-collapse styleId="condision" title="検索条件" state="true" theme="primary">

        <%-- ログイン ID--%>
        <div class="form-group">
            <label for="txt-cndLoginId" class="col col-xs-12 col-sm-3 col-md-2 control-label">ログインID</label>
            <div class="col col-xs-12 col-sm-6 col-md-7">
                <html:text property="cndLoginId" styleId="txt-cndLoginId" styleClass="form-control"/>
            </div>
        </div>

        <%-- ユーザ --%>
        <div class="form-group">
            <label for="txt-cndUserName" class="col col-xs-12 col-sm-3 col-md-2 control-label">ユーザ名</label>
            <div class="col col-xs-12 col-sm-6 col-md-7">
                <html:text property="cndUserName" styleId="txt-cndUserName" styleClass="form-control"/>
            </div>
        </div>

        <%-- 検索ボタン --%>
        <div class="form-group" style="margin-bottom: 0;">
            <div class="col-xs-offset-5 col-sm-offset-5">
                <s:submit property="index" value="　検　索　" styleClass="btn btn-primary"/>
            </div>
        </div>

    </bs:panel-collapse>

    <%-- 検索結果一覧 --%>
    <div style="margin-bottom: 20px;">
        <table id="dataTable" style="display: none; width: 100%;" class="table rd-dataTableStyle">
            <thead>
                <tr>
                    <th>ログインID</th>
                    <th>ユーザ名</th>
                    <th>権限</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="o" items="${rdUsers}">
                <tr>
                    <td class="loginId">
                        ${f:h(o.loginId)}
                        <input type="hidden" class="hid-dataTable-keyId" value="<c:out value="${o.userId}" />" disabled="disabled" />
                    </td>
                    <td class="userName">${f:h(o.userName)}</td>
                    <td class="userRoleName">${f:h(o.rdUserRole.userRoleName)}</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

</fieldset>

</s:form>

<rd:dtAjaxEditor-list
    urlEdit     = "/user/edit"
    urlAddNew   = "/user/addNew"
    urlDelete   = "/user/delete"
    dataTableId = "dataTable"
    addNewId    = "lnk-addNew"
/>

<%-- ------------------------------------------------- --%>
<script src="<%= request.getContextPath() %>/asset/js/plugin/jquery.reddog.functionKeyEvent.js"></script>

<script type="text/javascript">

$(function() {
    // placeholderを追加
    $('#txt-cndLoginId').attr('placeholder', 'ログインID');
    $('#txt-cndUserName').attr('placeholder', 'ユーザ名');

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
