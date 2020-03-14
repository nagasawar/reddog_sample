<%@tag pageEncoding="utf-8"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@tag import="org.seasar.framework.util.StringUtil"%>
<%@tag import="reddog_sample.util.helper.JsValidBuilder"%>

<%--
 *   select.tag
 *
 *   プルダウンを作成する
 *   ※optionも一緒に作成される
 *
 *   @param property         バインド値を指定する。必須。
 *   @param styleId          リストのidを指定する。必須。
 *   @param styleClass       リストのcssクラスを指定する。
 *   @param optionProperty   optionのpropertyを指定する。必須。
 *   @param optionLabel      optionのlabelを指定する。必須。
 *   @param optionValue      optionのvalueを指定する。必須。
 *   @param tabindex         タブインデックスを指定する。
 *   @param jscheck          javascriptによる入力チェックを行う。[初期値=false]
 *                           エラーメッセージ出力先も「rdf:errors」を設定すること
 *
 * 【JSP設定例】
 *   <rdf:select
 *      property       = "departmentId"
 *      styleId        = "sel-department"
 *      styleClass     = "form-control"
 *      optionProperty = "departments"
 *      optionLabel    = "departmentName"
 *      optionValue    = "departmentId"
 *      jscheck        = "true"
 *      />
 *   <%-- エラーメッセージ出力先 --%
 *   <rdf:errors property = "departmentId"/>
 --%>
<%@ attribute name="property"       required="true" %>
<%@ attribute name="styleId" %>
<%@ attribute name="styleClass" %>
<%@ attribute name="optionProperty" required="true" %>
<%@ attribute name="optionLabel"    required="true" %>
<%@ attribute name="optionValue"    required="true" %>
<%@ attribute name="tabindex" %>
<%@ attribute name="jscheck" type="java.lang.Boolean" %>

<%-- Boolean初期値指定 --%>
<%
    jscheck = (jscheck == null)?  false: true;
%>

<%-- ---------------------------------------------------------- --%>
<html:select property="${property}" styleId="${styleId}" styleClass="${styleClass}" tabindex="${tabindex}">
    <html:option value="">&nbsp;</html:option>
    <html:optionsCollection property="${optionProperty}" label="${optionLabel}" value="${optionValue}" />
</html:select>

<%-- ---------------------------------------------------------- --%>
<% if (jscheck) { %>
<%= (new JsValidBuilder(request, property,
        "[name="+ property+ "]",
        "blur",
        "$(this).val()",
        "0")).toString() %>
<% } %>
