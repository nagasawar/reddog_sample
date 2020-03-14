<%@tag pageEncoding="utf-8"%>
<%@tag import="org.seasar.framework.util.StringUtil"%>
<%@tag import="reddog_sample.util.helper.JsValidBuilder"%>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html"%>

<%--
 *   errors.tag
 *
 *   項目用に対してのエラーメッセージを表示する
 *
 *   @param property    バインド値を指定する。必須。
 *
 * 【JSP設定例】
 *   <rdf:errors property = "note"/>
 --%>
<%@ attribute name="property" required="true" %>

<%-- ---------------------------------------------------------- --%>
<div id="${property}-error-msg" class="rd-js-error-msg">
    <html:errors property="${property}"
                 header="err.head"
                 footer="err.foot"
                 prefix="err.pre"
                 suffix="err.suf"
                 />
</div>

