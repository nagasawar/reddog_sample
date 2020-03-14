<%@ tag pageEncoding="utf-8"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://sastruts.seasar.org/functions" %>

<%--
 *   dtAjaxEditor-edit.tag
 *
 *   datatable 新規作成処理。
 *   ※必ず呼び出し元で<s:form>の中に含めること。
 *
 *   新規作成ダイアログのサブミットイベントでajax通信で作成処理を行い、
 *   その後、一覧に値を反映させる処理を作成する。
 *
 *   @param formId  編集作成ダイアログ内のformのidを指定する。必須。
 *   @param applies Form内の値を、一覧に反映するためのkey: value定義。
 *                  複数ある場合は「|」で区切る。
 *                  例) employeeId: #txt-employeeId | fullname: #txt-fullname
 *
 *                  checkboxの場合は、#id[trueの表示文字,falseの表示文字]で定義する。
 *                  例) adminFlg: #chk-adminFlg[管理者,一般]
 *
 *                  selectまたはradioの場合は、#id[値1=名称1,値2=名称2...]で定義する。
 *                  例) gender: .rdo-gender[1=男,2=女]
 *
 *   【jsp使用例】
 *       <rd:dtAjaxEditor-edit
 *           url     = "/employee/editUpdate"
 *           formId  = "modal-edit-form"
 *           applies = "employeeId: #txt-employeeId | fullName: #txt-fullName | departmentName: #sel-department[${departmentIdNameStr}] | gender: .rdo-gender[1=男,2=女]"
 *       />
 --%>
<%@ attribute required="true" name="formId"%>
<%@ attribute required="true" name="applies"%>

<%-- ---------------------------------------------------------- --%>
<html:hidden property="ajaxEditorKeyId" styleId="hid-edit-keyId"/>
<html:hidden property="closeAjaxEditor" />

<script type="text/javascript">
$(function() {

    dtAjaxEditor.submitEventEdit(
            /*formId=*/      '${formId}',
            /*hiddenKeyId=*/ 'hid-edit-keyId',
            /*applies=*/     '${applies}'
    );

    jQuery.fn.initFocus();
    jQuery.fn.initSelectText();
});
</script>

