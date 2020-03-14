<%--
業務系の共通エラー
berrorとはBuisinessErrorの略である
 --%>
<%@page pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">
<tiles:put name="title" value="エラー" />
<tiles:put name="content" type="string">

${errorMsg}

</tiles:put>
</tiles:insert>
