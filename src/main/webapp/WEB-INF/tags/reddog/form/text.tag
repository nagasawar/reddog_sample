<%@tag pageEncoding="utf-8"%>
<%@tag import="org.seasar.framework.util.StringUtil"%>
<%@tag import="reddog_sample.util.helper.JsValidBuilder"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html"%>

<%--
 *   text.tag
 *
 *   テキストボックスを作成する
 *
 *   @param property    バインド値を指定する。必須。
 *   @param styleId     リストのidを指定する。
 *   @param styleClass  リストのcssクラスを指定する。
 *   @param tabindex    タブインデックスを指定する。
 *   @param jscheck     javascriptによる入力チェックを行う。[初期値=false]
 *                      エラーメッセージ出力先も「rdf:errors」を設定すること
 *
 * 【JSP設定例】
 *   <rdf:text
 *      property    = "departmentName"
 *      styleId     = "txt-departmentName"
 *      styleClass  = "form-control"
 *      jscheck     = "true"
 *      />
 *   <%-- エラーメッセージ出力先 --%
 *   <rdf:errors property = "departmentName"/>
 --%>
<%@ attribute name="property" required="true" %>
<%@ attribute name="styleId" %>
<%@ attribute name="style" %>
<%@ attribute name="styleClass" %>
<%@ attribute name="tabindex" %>
<%@ attribute name="jscheck" type="java.lang.Boolean" %>

<%-- Boolean初期値指定 --%>
<%
    jscheck = (jscheck == null)?  false: true;
%>

<%-- ---------------------------------------------------------- --%>
<html:text property="${property}"
           styleId="${styleId}"
           style="${style}"
           styleClass="${styleClass}"
           tabindex="${tabindex}"
           />

<%-- ---------------------------------------------------------- --%>
<% if (jscheck) { %>
<%= (new JsValidBuilder(request, property,
        "[name="+ property+ "]",
        "blur",
        "$(this).val()",
        "0")).toString() %>
<% } %>
