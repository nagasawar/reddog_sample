<%@page import="reddog_sample.util.helper.Html"%>
<%@page pageEncoding="UTF-8"%>

<rdf:errorSummary />

<s:form method="POST" action="/employee/addNewCreate/" styleClass="form-horizontal" styleId="modal-addNew-form">

<div class="well well-small">

    <%-- 入力部分 --%>
    <%@ include file="../layout-parts/parts-form.jsp"%>

    <%-- ボタン部分 --%>
    <div class="form-group" style="margin-bottom: 0;">
        <div class="col-xs-offset-5">
            <s:submit property="index" value="作成" styleClass="btn btn-primary"/>
        </div>
    </div>

</div>

<rd:dtAjaxEditor-addNew
    formId  = "modal-addNew-form"
    applies = "employeeId: #txt-employeeId | fullName: #txt-fullName | maidenNameFlg: #chk-maidenNameFlg[○,-] | departmentName: #sel-department[${departmentIdNameStr}] | gender: .rdo-gender[${genderIdNameStr}]"
/>

</s:form>

<script type="text/javascript">
$(function() {
    $('#modal-addNew-form').enterNext();
});
</script>
