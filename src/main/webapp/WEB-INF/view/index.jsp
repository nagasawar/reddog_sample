<%@page pageEncoding="UTF-8"%>
<tiles:insert template="/WEB-INF/view/common/layout.jsp" flush="true">
<tiles:put name="title" value="トップページ" />
<tiles:put name="content" type="string">

<fieldset>
    <legend>トップページ</legend>
    <p>
    こんにちは ${f:h(userName)} さん
    </p>
</fieldset>

</tiles:put>
</tiles:insert>