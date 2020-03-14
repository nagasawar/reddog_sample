<%@ tag pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://sastruts.seasar.org/functions" %>

<%--
 *   dtAjaxEditor-list.tag
 *
 *   更新、削除リンクを追加したdatatableを作成する。
 *
 *   @param dataTableId  datatableとなるtableのIdを指定する。必須。
 *   @param urlEdit      編集表示用のURLを指定する。必須。
 *   @param urlDelete    ajaxによる削除用のURLを指定する。必須。
 *   @param urlAddNew    新規作成表示用のURLを指定する。必須。
 *   @param addNewId     新規作成ボタンのidを指定する。必須。
 *   @param width        新規作成 or 編集のダイアログの幅を指定する。
 *
 *   【jsp使用例】
 *     <div style="margin-bottom: 20px;">
 *          <table id="dataTable" style="display: none; width: 100%;" class="table rd-dataTableStyle">
 *              <thead>
 *                  <tr>
 *                      <th>社員ID</th>
 *                      <th>氏名</th>
 *                      <th>所属</th>
 *                      <th>性別</th>
 *                  </tr>
 *              </thead>
 *              <tbody>
 *                  <c:forEach var="o" items="${employees}">
 *                  <tr>
 *                      <td class="employeeId">
 *                          ${f:h(o.employeeId)}
 *                          <input type="hidden" class="hid-dataTable-keyId" value="<c:out value="${o.employeeId}" />" disabled="disabled" />
 *                      </td>
 *                      <td class="fullName">${f:h(o.fullName)}</td>
 *                      <td class="departmentName">${f:h(o.department.departmentName)}</td>
 *                      <td class="gender">${f:h(o.genderName)}</td>
 *                  </tr>
 *                  </c:forEach>
 *              </tbody>
 *          </table>
 *      </div>
 *
 *      <rd:dtAjaxEditor-list
 *           urlEdit     = "/employee/edit"
 *           urlAddNew   = "/employee/addNew"
 *           urlDelete   = "/employee/delete"
 *           dataTableId = "dataTable"
 *           addNewId    = "lnk-addNew"
 *           widh        = 600
 *       />
 --%>
<%@ attribute name="dataTableId" required="true"%>
<%@ attribute name="urlEdit"     required="true"%>
<%@ attribute name="urlDelete"   required="true"%>
<%@ attribute name="urlAddNew"   required="true"%>
<%@ attribute name="addNewId"    required="true"%>
<%@ attribute name="width"%>

<%-- ---------------------------------------------------------- --%>
<link rel="stylesheet" href="<%= request.getContextPath() %>/asset/package/dataTables/css/jquery.dataTables.css" media="screen">
<script src="<%= request.getContextPath() %>/asset/package/dataTables/js/jquery.dataTables.js"></script>
<script src="<%= request.getContextPath() %>/asset/package/dataTables/js/jquery.dataTables.fnAddTr.js"></script>
<script src="<%= request.getContextPath() %>/asset/js/plugin/jquery.reddog.dataTables.dtAjaxEditor.js"></script>

<link rel="stylesheet" href="<%= request.getContextPath() %>/asset/package/dataTables/css/dataTables.jqueryui.min.css" media="screen">
<script src="<%= request.getContextPath() %>/asset/package/dataTables/js/dataTables.jqueryui.min.js"></script>

<div id="modal-area"></div>

<script type="text/javascript">

<%-- ダイアログ内でも使うので、「$(function() ...)」の外で定義すること。--%>
var dtAjaxEditor =
    new DtAjaxEditor(
        /*dialogId=*/     '#modal-area',
        /*dataTableId=*/  '#${dataTableId}',
        /*optDataTable=*/ { stateSave:true, pageSave:true },
        /*urlEdit=*/      '<%=request.getContextPath()%>/${urlEdit}',
        /*urlDelete=*/    '<%=request.getContextPath()%>/${urlDelete}',
        /*argWidth*/      '${width}'
    );

$(function() {

    dtAjaxEditor.clickEventAddNew(
        /*url=*/ '<%=request.getContextPath()%>/${urlAddNew}',
        /*clickTarget=*/ $('#${addNewId}')
    );
});
</script>

