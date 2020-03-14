<%@ tag pageEncoding="utf-8"%>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://sastruts.seasar.org/functions" %>
<%@ taglib prefix="s" uri="http://sastruts.seasar.org" %>

<%--
 *   linkSubmit.tag
 *
 *   linkとFormを生成し、linkをクリックしたときにFormのsubmitをさせる。
 *   ※formタグ内部には配置しないこと。formの入れ子になってしまうため。
 *
 *   @param styleId   一意のIdを指定する。必須。
 *   @param action    Formのactionを指定する。必須。
 *   @param target    Formのtargetを指定する。
 *   @param linkValue リンクの名称を指定する。必須。
 *   @param linkClass リンクのスタイルクラスを指定する。
 *   @param applies   Form内にhiddenを配置する場合、text値を、Form内のhiddenに値を当てる。
 *                    hiddenはname部分を指定する。
 *                    複数ある場合は「|」で区切る。
 *                    hidden1: text1 | hidden2: text2
 *                    例) employeeId: #txt-employeeId | fullname: #txt-fullname
 *
 * 【JSP設定例】
 *   <rd:linkSubmit
 *   	styleId   = "lnk-outputCsv"
 *   	action    = "/employee/outputCsv/"
 *   	linkValue = "F10 CSV出力"
 *   	linkClass = "btn btn-success"
 *   	applies   = "employeeId: #txt-employeeId
 *   	>
 *   	<html:hidden property="employeeId"/>
 *   </rd:linkSubmit>
 *
 --%>
<%@ attribute name="styleId"   required="true" %>
<%@ attribute name="action"    required="true" %>
<%@ attribute name="target" %>
<%@ attribute name="linkValue" required="true" %>
<%@ attribute name="linkClass" %>
<%@ attribute name="applies" %>

<%-- ---------------------------------------------------------- --%>

<a href="#" id="${styleId}" class="${linkClass}">${linkValue}</a>

<s:form method="POST" target="${target}" styleId="rd-form-${styleId}" action="${action}" style="display: none;">
    <jsp:doBody/>
</s:form>

<script type="text/javascript">

<%--
    一覧の行へ当てはめるための指定文字列を配列に変換する

    例)
    "loginId: #txt-loginId | userName: #txt-userName | adminFlg: #txt-adminFlg"
      ↓
    {loginId: '#txt-loginId', userName: '#txt-userName', adminFlg: '#txt-adminFlg'}
 --%>
function applies2applyArray(applies) {

    var applyArray = {};

    var sp1 = applies.split('|');

    $.each(sp1, function(i) {
        var tmp = jQuery.trim(sp1[i]);
        var sp2 = tmp.split(':');
        var key = jQuery.trim(sp2[0]);

        <%-- sp2[1]に「:」が入っていると、さらに配列ができてしまうため、sp2[0]を省いて値だけを取得する --%>
        var val = jQuery.trim(tmp.replace(sp2[0]+':', ''));

        applyArray[key] = val;
    });

    return applyArray;
}

$(function() {
    $('#${styleId}').click(function() {

        var formId = '#rd-form-${styleId}';

        <%-- appliesが指定されている場合は、hiddenに割り当てる --%>
        <c:if test="${applies != null}">
            var applyArray = applies2applyArray('${applies}');

            for (var key in applyArray) {

                var selector = applyArray[key];

                var obj = $(selector);
                var hidden = $(formId).find(':hidden[name='+ key+ ']');

                if (!obj.exists()) {
                    break;
                }

                if (obj.get(0).tagName == 'INPUT' && obj.attr('type') == 'text') {
                    hidden.val(obj.val());
                }
                else if (obj.get(0).tagName == 'SELECT') {
                    hidden.val(obj.val());
                }
            }
        </c:if>

        $(formId).submit();
    });
});
</script>
