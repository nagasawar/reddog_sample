<!DOCTYPE html>
<%@page import="reddog_sample.util.helper.Html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="org.seasar.framework.container.SingletonS2Container"%>
<%@page import="reddog_sample.ignore.dto.RdUserDto"%>
<html>
<head>
<meta charset="utf-8">
<title><tiles:getAsString name="title" /></title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/asset/package/jquery-ui/css/jquery-ui.css" media="screen">
<%-- スマホ対応
<link rel="stylesheet" href="<%= request.getContextPath() %>/asset/package/bootstrap/css/bootstrap-responsive.css" media="screen">
--%>
<link rel="stylesheet" href="<%= request.getContextPath() %>/asset/package/bootstrap/css/bootstrap.css" media="screen">
<link rel="stylesheet" href="<%= request.getContextPath() %>/asset/css/base.css" media="screen">
<link rel="stylesheet" href="<%= request.getContextPath() %>/asset/css/reddog.css" media="screen">

<script src="<%= request.getContextPath() %>/asset/js/jquery.js"></script>
<script src="<%= request.getContextPath() %>/asset/js/jquery.cookie.js"></script>
<script src="<%= request.getContextPath() %>/asset/package/bootstrap/js/bootstrap.js"></script>
<script src="<%= request.getContextPath() %>/asset/package/jquery-ui/js/jquery-ui.js"></script>
<script src="<%= request.getContextPath() %>/asset/js/plugin/jquery.reddog.init.js"></script>
<script src="<%= request.getContextPath() %>/asset/js/plugin/jquery.reddog.func.js"></script>
<script src="<%= request.getContextPath() %>/asset/js/plugin/jquery.reddog.func.const.js"></script>
<script src="<%= request.getContextPath() %>/asset/js/plugin/jquery.reddog.enterNext.js"></script>
</head>
<body>

<% RdUserDto rdUserDto = SingletonS2Container.getComponent(RdUserDto.class); %>

<%-- ヘッダー部分 --%>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">

            <%-- ログイン時のみ表示 --%>
            <% if (rdUserDto.isLogin()) { %>
                <button aria-expanded="false" data-target="#bs-example-navbar-collapse-9" data-toggle="collapse" class="navbar-toggle collapsed" type="button">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            <% } %>
            <s:link href="/" styleClass="navbar-brand">仮システム</s:link>
        </div>

        <%-- ログイン時のみ表示 --%>
        <% if (rdUserDto.isLogin()) { %>
        <div id="bs-example-navbar-collapse-9" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">

                <% if (rdUserDto.isAllowedRole("/employee")) { %>
                <li class="<%= Html.bsMenuActiveCls("/employee/index.jsp") %>">
                    <s:link href="/employee">社員検索</s:link>
                </li>
                <% } %>

                <% if (rdUserDto.isAllowedRole("/employeeBulk")) { %>
                <li class="<%= Html.bsMenuActiveCls("/employeeBulk/index.jsp") %>">
                    <s:link href="/employeeBulk">社員一括編集</s:link>
                </li>
                <% } %>

                <% if (rdUserDto.isAllowedRole("/employeeOutput")) { %>
                <li class="<%= Html.bsMenuActiveCls("/employeeOutput/index.jsp") %>">
                    <s:link href="/employeeOutput">社員出力</s:link>
                </li>
                <% } %>

                <% if (rdUserDto.isAllowedRole("/department")
                        || rdUserDto.isAllowedRole("/user")) { %>

                <li class="dropdown <%= Html.bsMenuActiveCls("/department/index.jsp", "/user/index.jsp") %>">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        マスタ <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">

                        <% if (rdUserDto.isAllowedRole("/department")) { %>
                        <li class="<%= Html.bsMenuActiveCls("/department/index.jsp") %>">
                            <s:link href="/department">所属マスタ</s:link>
                        </li>
                        <% } %>

                        <% if (rdUserDto.isAllowedRole("/user")) { %>
                        <li class="<%= Html.bsMenuActiveCls("/user/index.jsp") %>">
                            <s:link href="/user">ユーザ検索</s:link>
                        </li>
                        <% } %>

                        <% if (rdUserDto.isAllowedRole("/userRole")) { %>
                        <li class="<%= Html.bsMenuActiveCls("/userRole/index.jsp") %>">
                            <s:link href="/userRole">ユーザ権限</s:link>
                        </li>
                        <% } %>
                    </ul>
                </li>
                <% } %>

            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><s:link href="/logout">ログアウト</s:link></li>
            </ul>
        </div>
        <% } %>
    </div>
</nav>

<%-- コンテンツ部分 --%>
<div class="container ht-container">

    <%-- 成功メッセージ --%>
    <html:messages id="message" message="true">
    <div class="alert alert-success alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
        ${message}
    </div>
    </html:messages>

    <%-- コンテンツ --%>
    <tiles:insert attribute="content" />
</div>

</body>
</html>
