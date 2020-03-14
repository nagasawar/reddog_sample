<%@ tag pageEncoding="utf-8"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://sastruts.seasar.org/functions" %>

<%--
 *   panel-collapse.tag
 *
 *   Twitter Bootstrapの開閉パネルを生成する
 *
 *   @param styleId 一意のIdを指定する。必須。
 *   @param title パネルのタイトルを指定する。必須。
 *   @param state 開閉状態を保持するか指定する。初期値： false
 *   @param theme 色を指定する。
 *                指定値： primary, success, info, warning, danger
 *                初期値： primary
 --%>
<%@ attribute name="styleId" required="true" %>
<%@ attribute name="title"   required="true" %>
<%@ attribute name="state"   type="java.lang.Boolean" %>
<%@ attribute name="theme" %>

<%-- ---------------------------------------------------------- --%>

<% String t = "primary"; %>
<c:if test="${theme != null}">
  <% t = theme; %>
</c:if>

<div id="${styleId}" class="panel panel-<%= t %>">
    <div class="panel-heading">
        <h3 class="panel-title">
            <a data-toggle="collapse" data-parent="#accordion" href="#${styleId}-collapse">
                ${title}
            </a>
        </h3>
    </div>

    <div id="${styleId}-collapse" class="panel-collapse collapse">
        <div class="panel-body">
             <jsp:doBody/>
        </div>
    </div>
</div>

<%-- 開閉状態をcookieで保持する --%>
<c:if test="${state}">

    <script type="text/javascript">
    $(function() {

        var id         = '${styleId}';
        var colId      = '${styleId}-collapse';
        var cookieName = 'activeAccordionGroup';

        $('#'+ id).on('shown.bs.collapse', function () {
            $.cookie(cookieName, colId);
        });

        $('#'+ id).on('hidden.bs.collapse', function () {
            $.removeCookie(cookieName);
        });

        var last = $.cookie(cookieName);
        if (last != null) {
            $('#'+ colId).removeClass('in')
                         .addClass('in');
        }
    });
    </script>
</c:if>
