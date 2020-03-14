<%@ tag pageEncoding="utf-8"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://sastruts.seasar.org/functions" %>
<%@ taglib prefix="s" uri="http://sastruts.seasar.org" %>
<%@ tag import="reddog_sample.util.helper.JsValidBuilder"%>

<%--
 *   combobox.tag
 *
 *   コードと名称のプルダウンリストを作成する。
 *   ※formタグ内部に配置すること。html:selectをしようするため。
 *
 *   @param property         バインド値を指定する。必須。
 *   @param styleId          リストのidを指定する。必須。
 *   @param styleClass       リストのcssクラスを指定する。
 *   @param invalid          入力された値に合致するデータが見つからなかった場合に「見つかりません」と
 *                           表示する/しない → true/false。[初期値=false]
 *   @param showName         選択中の名称部分を表示する/しない → true/false。[初期値=true]
 *   @param height           リストの高さをpxで指定する。
 *   @param width            リストの幅をpxで指定する。
 *   @param optionProperty   optionのpropertyを指定する。必須。
 *   @param optionLabel      optionのlabelを指定する。必須。
 *   @param optionValue      optionのvalueを指定する。必須。
 *   @param jscheck          javascriptによる入力チェックを行う。[初期値=false]
 *                           エラーメッセージ出力先も「rdf:errors」を設定すること
 *
 * 【JSP設定例】
 *   <rd:combobox
 *      property       = "employeeId"
 *      styleId        = "cmb-employeeId"
 *      styleClass     = "form-control"
 *      width          = "300px"
 *      optionProperty = "employees"
 *      optionLabel    = "fullname"
 *      optionValue    = "employeeId"
 *      jscheck        = "true"
 *      />
 *   <%-- エラーメッセージ出力先 --%
 *   <rdf:errors property = "employeeId"/>
 --%>
<%@ attribute name="property"   required="true" %>
<%@ attribute name="styleId"    required="true" %>
<%@ attribute name="styleClass" %>
<%@ attribute name="invalid"    type="java.lang.Boolean" %>
<%@ attribute name="showName"   type="java.lang.Boolean" %>
<%@ attribute name="height" %>
<%@ attribute name="width" %>
<%@ attribute name="optionProperty" required="true" %>
<%@ attribute name="optionLabel"    required="true" %>
<%@ attribute name="optionValue"    required="true" %>
<%@ attribute name="jscheck" type="java.lang.Boolean" %>

<%-- Boolean初期値指定 --%>
<%
    invalid  = (invalid == null)?  false: true;
    showName = (showName == null)? true:  false;
    jscheck  = (jscheck == null)?  false: true;
%>

<%-- ---------------------------------------------------------- --%>
<script src="<%= request.getContextPath() %>/asset/js/plugin/jquery.reddog.combobox.js"></script>

<html:select property="${property}" styleId="${styleId}" styleClass="${styleClass}">
    <html:option value="">&nbsp;</html:option>
    <html:optionsCollection property="${optionProperty}" label="${optionLabel}" value="${optionValue}" />
</html:select>

<%
    String js_invalid       = (invalid)? "true": "false";
    String js_showName      = (showName)? "true": "false";
    boolean js_isHeight     = (height != null);
    boolean js_isStyleClass = (styleClass != null);
    boolean js_isWidth      = (width != null);
%>

<script type="text/javascript">
<%-- enterNextより後に読み込ませたいためsetTimeoutを使用して10ms遅らせる --%>
$(function() {
    setTimeout(function() {

        $('#${styleId}').combobox({
             invalid:  <%= js_invalid %>
            ,showName: <%= js_showName %>

            <% if (js_isHeight) { %>
                ,height: '${height}'
            <% } %>

            <% if (js_isStyleClass) { %>
                ,inputAddClass: '${styleClass}'
            <% } %>

            ,inputStyle: { display: 'inline'
                <% if (js_isWidth) { %>
                    ,width: '${width}'
                <% } %>
            }
        });

    }, 10);
});
</script>

<%-- ---------------------------------------------------------- --%>
<% if (jscheck) { %>
<%= (new JsValidBuilder(request, property,
        "#"+ styleId+ "-input",
        "blur",
        "$(this).val()",
        "20")).toString() %>
<% } %>
