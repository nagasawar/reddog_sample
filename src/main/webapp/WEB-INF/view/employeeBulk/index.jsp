<%@page pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">
<tiles:put name="title" value="社員一括編集" />
<tiles:put name="content" type="string">

<html:errors/>

<ul class="breadcrumb">
      <li><s:link href="/">TOP</s:link>
      <li class="active">社員一括編集</li>
</ul>

<s:form method="POST" action="/employeeBulk/editUpdate/" styleClass="form-horizontal">

<fieldset>
    <legend>社員一括編集</legend>

    ${htBuilder}
    <html:hidden property="jsonStr" styleId="dataTable-hidden" />

    <%-- ボタン部分 --%>
    <div class="form-group" style="margin: 10px 0 20px 0;">
        <div class="col-xs-offset-5">
            <s:submit property="index" value="更新" styleClass="btn btn-primary"/>
        </div>
    </div>

</fieldset>

</s:form>

</tiles:put>
</tiles:insert>
