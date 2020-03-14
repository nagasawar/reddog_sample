<%@page pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">
<tiles:put name="title" value="社員検索" />
<tiles:put name="content" type="string">

<ul class="breadcrumb">
      <li><s:link href="/">TOP</s:link>
      <li class="active">社員検索</li>
</ul>

<fieldset>
    <legend>社員検索</legend>

    <div align="right" style="margin-bottom: 5px;">
        <s:link href="addNew" styleId="lnk-addNew" styleClass="btn btn-success">F9 新規作成</s:link>

        <%-- CSV出力 --%>
        <rd:linkSubmit styleId="lnk-outputCsv" action="/employee/outputCsv/?url=/employee" linkValue="F10 CSV出力" linkClass="btn btn-success" />

        <%-- PDF出力 --%>
        <rd:linkSubmit styleId="lnk-outputPDF" action="/employee/outputPDF/?url=/employee" linkValue="F11 PDF出力" target="_blank" linkClass="btn btn-success" />

        <%-- Excel出力 --%>
        <rd:linkSubmit styleId="lnk-outputExcel" action="/employee/outputExcel/?url=/employee" linkValue="F12 Excel出力" linkClass="btn btn-success" />

        <s:link href="#" styleId="lnk-reload" styleClass="btn btn-success">F5 画面更新</s:link>
    </div>

    <%-- 検索結果一覧 --%>
    <div style="margin-bottom: 20px;">
        <table id="dataTable" style="display: none; width: 100%;" class="table rd-dataTableStyle">
            <thead>
                <tr>
                    <th>社員ID</th>
                    <th>氏名</th>
                    <th>旧姓</th>
                    <th>所属</th>
                    <th>性別</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="o" items="${employees}">
                <tr>
                    <td class="employeeId">
                        ${f:h(o.employeeId)}
                        <input type="hidden" class="hid-dataTable-keyId" value="<c:out value="${o.employeeId}" />" disabled="disabled" />
                    </td>
                    <td class="fullName">${f:h(o.fullName)}</td>
                    <td class="maidenNameFlg">
                        <c:choose>
                            <c:when test="${o.maidenNameFlg}">○</c:when>
                            <c:otherwise>-</c:otherwise>
                        </c:choose>
                    </td>
                    <td class="departmentName">${f:h(o.department.departmentName)}</td>
                    <td class="gender">${f:h(o.genderName)}</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

</fieldset>

<rd:dtAjaxEditor-list
    urlEdit     = "/employee/edit"
    urlAddNew   = "/employee/addNew"
    urlDelete   = "/employee/delete"
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

    // F10 CSV出力ボタン
    $(document).functionKeyEvent({
        fkname: RD_FK10,
        event: function() {
            $('#lnk-outputCsv').trigger('click');
        }
    });

    // F11 PDF出力ボタン
    $(document).functionKeyEvent({
        fkname: RD_FK11,
        event: function() {
            $('#lnk-outputPDF').trigger('click');
        }
    });

    // F12 Excel出力ボタン
    $(document).functionKeyEvent({
        fkname: RD_FK12,
        event: function() {
            $('#lnk-outputExcel').trigger('click');
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
