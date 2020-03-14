<%@page pageEncoding="UTF-8"%>
<%@page import="reddog_sample.util.helper.Html"%>
<%@page import="reddog_sample.util.helper.JsValidBuilder"%>
<% JsValidBuilder.setRdForm(request, "employeeOutput_employeeOutputForm"); %>

<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">
<tiles:put name="title" value="社員出力" />
<tiles:put name="content" type="string">

<html:errors/>

<ul class="breadcrumb">
      <li><s:link href="/">TOP</s:link>
      <li class="active">社員出力</li>
</ul>

<fieldset>
    <legend>社員出力</legend>

    <s:form>
        <div class="form-group">
            <label for="employeeId" class="col control-label">社員ID<rd:markRequired /></label>
            <div class="col <%= Html.bsErrCls("employeeId") %>">
                <rdf:combobox
                    property       = "employeeId"
                    styleClass     = "form-control"
                    styleId        = "sel-employeeId"
                    width          = "300px"
                    optionProperty = "employees"
                    optionLabel    = "fullname"
                    optionValue    = "employeeId"
                    jscheck        = "true"
                    />
                <rdf:errors property="employeeId"/>
            </div>
        </div>
    </s:form>


    <%-- ボタン部分 --%>
    <div class="form-group" style="margin: 10px 0 20px 0;">
         <div>

            <%-- PDF出力 --%>
            <rd:linkSubmit styleId="lnk-outputPDF" action="/employeeOutput/outputPDF/?url=/employeeOutput" linkValue="PDF出力" target="_blank" linkClass="btn btn-primary" applies="employeeId: #sel-employeeId">
                <html:hidden property="employeeId"/>
            </rd:linkSubmit>

            <%-- Excel出力 --%>
            <rd:linkSubmit styleId="lnk-outputExcel" action="/employeeOutput/outputExcel/?url=/employeeOutput" linkValue="Excel出力" linkClass="btn btn-primary" applies="employeeId: #sel-employeeId">
                <html:hidden property="employeeId"/>
            </rd:linkSubmit>

        </div>
    </div>

</fieldset>

<%-- ------------------------------------------------- --%>

<script type="text/javascript">
$(function() {

    // Excel出力ボタンクリック時にエラーメッセージを消す
    $('#lnk-outputExcel').click(function() {
        $('.alert-danger').remove();
    });
});
</script>

</tiles:put>
</tiles:insert>
