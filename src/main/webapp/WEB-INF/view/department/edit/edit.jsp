<%@page pageEncoding="UTF-8"%>

<rdf:errorSummary />

<s:form method="POST" action="/department/editUpdate/" styleClass="form-horizontal" styleId="modal-edit-form">

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
    applies = "departmentId: #txt-departmentId | departmentName: #txt-departmentName"
/>

</s:form>

<script type="text/javascript">
$(function() {
    $('#modal-edit-form').enterNext();
});
</script>
