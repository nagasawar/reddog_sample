<%@page pageEncoding="UTF-8"%>
<%@page import="reddog_sample.util.helper.Html"%>
<%@page import="reddog_sample.util.helper.JsValidBuilder"%>
<% JsValidBuilder.setRdForm(request, "employee_inputForm"); %>

<div id="XMLHttpRequest"></div>
<div id="textStatus"></div>
<div id="errorThrown"></div>

<%-- 新規作成、編集の入力部分 --%>

<%-- 社員ID --%>
<div class="form-group">
    <label for="txt-employeeId" class="col col-xs-12 col-sm-4 col-md-3 control-label">社員ID<rd:markRequired /></label>
    <div class="col col-xs-12 col-sm-6 col-md-7 <%= Html.bsErrCls("employeeId") %>">

        <%-- 新規作成の場合はテキストボックス --%>
        <c:if test="${addNewMode}">
            <rdf:text property="employeeId"
                      styleId="txt-employeeId"
                      styleClass="form-control rd-initFocus"
                      jscheck="true"
                      />
            <rdf:errors property="employeeId"/>
        </c:if>

        <c:if test="${!addNewMode}">
            <p class="form-control-static">${f:h(employeeId)}</p>
            <html:hidden property="employeeId" styleId="txt-employeeId"/>
        </c:if>
    </div>
</div>

<%-- 氏名 --%>
<div class="form-group">
    <label for="fullName" class="col col-xs-12 col-sm-4 col-md-3 control-label">氏名<rd:markRequired /></label>
    <div class="col col-xs-12 col-sm-6 col-md-7 <%= Html.bsErrCls("fullName") %>">
        <rdf:text property="fullName"
                  styleId="txt-fullName"
                  styleClass="form-control"
                  jscheck="true"
                  />
        <rdf:errors property="fullName"/>
    </div>
</div>

<%-- フリガナ --%>
<div class="form-group">
    <label for="kana" class="col col-xs-12 col-sm-4 col-md-3 control-label">フリガナ<rd:markRequired /></label>
    <div class="col col-xs-12 col-sm-6 col-md-7 <%= Html.bsErrCls("kana") %>">
        <rdf:text property="kana"
                  styleId="txt-kana"
                  styleClass="form-control"
                  jscheck="true"
                  />
        <rdf:errors property="kana"/>
    </div>
</div>

<%-- 旧姓 --%>
<div class="form-group">
    <label for="chk-maidenNameFlg" class="col col-xs-12 col-sm-4 col-md-3 control-label">旧姓</label>
    <div class="col col-xs-12 col-sm-6 col-md-7">
         <label class="checkbox-inline">
            <html:checkbox property="maidenNameFlg" styleId="chk-maidenNameFlg"/>
        </label>
    </div>
</div>

<%-- 所属 --%>
<div class="form-group">
    <label for="departmentId" class="col col-xs-12 col-sm-4 col-md-3 control-label">所属<rd:markRequired /></label>
    <div class="col col-xs-12 col-sm-6 col-md-7 <%= Html.bsErrCls("departmentId") %>">
         <rdf:select
           property       = "departmentId"
           styleId        = "sel-department"
           styleClass     = "form-control"
           optionProperty = "departments"
           optionLabel    = "departmentName"
           optionValue    = "departmentId"
           jscheck        = "true"
           />
        <rdf:errors property="departmentId"/>
     </div>
</div>

<%-- 性別 --%>
<div class="form-group <%= Html.bsErrCls("gender") %>">
    <label for="gender" class="col col-xs-12 col-sm-4 col-md-3 control-label">性別</label>
    <div class="col col-xs-12 col-sm-6 col-md-7">
        <c:forEach var="o" items="${genders}">
            <label><html:radio property="gender" styleClass="rdo-gender" value="${o.value}"/>${o.cname}</label>&nbsp;
        </c:forEach>
    </div>
</div>

<%-- 生年月日 --%>
<div class="form-group">
    <label for="birthday" class="col col-xs-12 col-sm-4 col-md-3 control-label">生年月日</label>
    <div class="col col-xs-12 col-sm-6 col-md-7 rd-datepicker-text <%= Html.bsErrCls("birthday") %>">
        <rdf:text property="birthday"
                  styleId="txt-birthday"
                  styleClass="form-control"
                  jscheck="true"
                  />
        <rdf:errors property="birthday"/>
    </div>
</div>

<%-- 郵便番号 --%>
<div class="form-group">
    <label for="txt-postCode" class="col col-xs-12 col-sm-4 col-md-3 control-label">郵便番号</label>
    <div class="col col-xs-12 col-sm-6 col-md-7 <%= Html.bsErrCls("postCode") %>">
        <rdf:text property="postCode"
                  styleId="txt-postCode"
                  styleClass="form-control"
                  jscheck="true"
                  />
        <rdf:errors property="postCode"/>
    </div>
</div>

<%-- 住所1 --%>
<div class="form-group">
    <label for="address1" class="col col-xs-12 col-sm-4 col-md-3 control-label">住所1</label>
    <div class="col col-xs-12 col-sm-6 col-md-7 <%= Html.bsErrCls("address1") %>">
        <rdf:text property="address1"
                  styleId="txt-address1"
                  styleClass="form-control"
                  jscheck="true"
                  />
        <rdf:errors property="address1"/>
    </div>
</div>

<%-- 住所2 --%>
<div class="form-group">
    <label for="address2" class="col col-xs-12 col-sm-4 col-md-3 control-label">住所2</label>
    <div class="col col-xs-12 col-sm-6 col-md-7 <%= Html.bsErrCls("address2") %>">
        <rdf:text property="address2"
                  styleId="txt-address2"
                  styleClass="form-control"
                  jscheck="true"
                  />
        <rdf:errors property="address2"/>
    </div>
</div>

<%-- 住所3 --%>
<div class="form-group">
    <label for="address3" class="col col-xs-12 col-sm-4 col-md-3 control-label">住所3</label>
    <div class="col col-xs-12 col-sm-6 col-md-7 <%= Html.bsErrCls("address3") %>">
        <rdf:text property="address3"
                  styleId="txt-address3"
                  styleClass="form-control"
                  jscheck="true"
                  />
        <rdf:errors property="address3"/>
    </div>
</div>

<%-- 電話番号 --%>
<div class="form-group">
    <label for="tel" class="col col-xs-12 col-sm-4 col-md-3 control-label">電話番号</label>
    <div class="col col-xs-12 col-sm-6 col-md-7 <%= Html.bsErrCls("tel") %>">
        <rdf:text property="tel"
                  styleId="txt-tel"
                  styleClass="form-control"
                  jscheck="true"
                  />
        <rdf:errors property="tel"/>
    </div>
</div>

<%-- 備考 --%>
<div class="form-group">
    <label for="txt-note" class="col col-xs-12 col-sm-4 col-md-3 control-label">備考</label>
    <div class="col col-xs-12 col-sm-6 col-md-7 <%= Html.bsErrCls("note") %>">
        <rdf:textarea property="note"
                      styleId="txt-note"
                      styleClass="form-control"
                      style="height: 200px;"
                      jscheck="true"
                      />
        <rdf:errors property="note"/>
    </div>
</div>

<script src="<%= request.getContextPath() %>/asset/js/plugin/jquery.reddog.autoInputKana.js"></script>
<script src="<%= request.getContextPath() %>/asset/js/plugin/jquery.ui.datepickerSeireki.js"></script>

<script type="text/javascript">
$(function() {
    // placeholderを追加
    $('#txt-employeeId').attr('placeholder', '(例) 001001');
    $('#txt-fullName').attr('placeholder', '(例) 山田　太郎');
    $('#txt-kana').attr('placeholder', '(例) ﾔﾏﾀﾞ ﾀﾛｳ');
    $('#txt-birthday').attr('placeholder', '(例) 1980/01/01');
    $('#txt-postCode').attr('placeholder', '(例) 1200001');
    $('#txt-address1').attr('placeholder', '(例) 東京都中央区');
    $('#txt-address2').attr('placeholder', '(例) 日本橋0-0-0');
    $('#txt-address3').attr('placeholder', '(例) マンション名');
    $('#txt-tel').attr('placeholder', '(例) 03-0000-0000');

    // 半角カナ変換
    $('#txt-fullName').autoInputKana({
        kanaId: 'txt-kana'
    });

    // datepicker
    $('#txt-birthday').datepickerSeireki({ contextPath: '<%=request.getContextPath()%>' });
});
</script>
