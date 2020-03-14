<%@tag pageEncoding="utf-8"%>
<%@tag import="reddog_sample.util.helper.Html"%>

<%--
 *   errorSummary.tag
 *
 *   エラーサマリを取得する
 *   「○件エラーがあります」
 *
 * 【JSP設定例】
 *   <rdf:errorSummary />
 --%>

<%-- ---------------------------------------------------------- --%>
<% String errSum = Html.errorSummary(); %>

<% if (!errSum.equals("")) { %>
<div class="alert alert-danger alert-dismissible" role="alert">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
    <%= errSum %>
</div>
<% } %>

