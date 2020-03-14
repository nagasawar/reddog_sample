<%@page pageEncoding="UTF-8"%>

<rdf:errorSummary />

<s:form method="POST" action="/employee/editUpdate/" styleClass="form-horizontal" styleId="modal-edit-form">

<div class="well well-small">

    <%-- 入力部分 --%>
    <%@ include file="../layout-parts/parts-form.jsp"%>

    <%-- ボタン部分 --%>
    <div class="form-group" style="margin-bottom: 0;">
        <div class="col-xs-offset-5">
            <s:submit property="index" value="更新" styleClass="btn btn-primary"/>
        </div>
    </div>
</div>

<rd:dtAjaxEditor-edit
    formId  = "modal-edit-form"
    applies = "employeeId: #txt-employeeId | fullName: #txt-fullName | maidenNameFlg: #chk-maidenNameFlg[○,-] | departmentName: #sel-department[${departmentIdNameStr}] | gender: .rdo-gender[${genderIdNameStr}]"
/>

</s:form>

<script type="text/javascript">
$(function() {
    $('#modal-edit-form').enterNext();
});
</script>
