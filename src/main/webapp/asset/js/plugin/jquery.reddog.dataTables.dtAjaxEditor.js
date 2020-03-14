/**
 * reddog DtAjaxEditor
 *
 * ◆以下の処理を行う
 *   ・ DataTable向けTableに「編集|削除」リンク列を追加。
 *   ・ DataTableの作成。
 *   ・ 新規ボタンにダイアログイベントを作成する
 *   ・ 新規作成ダイアログのsubmitイベントを作成する。
 *   ・ 編集ダイアログのsubmitイベントを作成する
 *
 * ◆前提外部ファイル
 *   ・ %contextPath%/asset/package/jquery-ui/css/jquery-ui.css
 *   ・ %contextPath%/asset/js/jquery.js
 *   ・ %contextPath%/asset/package/jquery-ui/js/jquery-ui.js
 *   ・ %contextPath%/asset/js/plugin/jquery.reddog.init.js
 *   ・ %contextPath%/asset/package/dataTables/css/dataTables.css
 *   ・ %contextPath%/asset/package/dataTables/js/jquery.dataTables.js
 *   ・ %contextPath%/asset/package/dataTables/js/jquery.dataTables.fnAddTr.js
 *   ・ %contextPath%/asset/js/plugin/jquery.reddog.dataTables.dtAjaxEditor.js
 *
 * ◆使い方
 * まず、list.jsp, addNew.jsp, edit.jsp画面を用意する。
 * list.jsp は、通常の画面表示。
 * addNew.jsp, edit.jsp は、ajax　で表示する。
 *
 * ************************************************************************************
 *   ○ list.jsp
 * ************************************************************************************
 *   // html
 *                                     :
 *                                     :
 *   <table id="dataTable" style="display: none;" class="reddog-dataTableStyle">
 *       <thead>
 *           <tr>
 *               <th>コード</th>
 *               <th>名称</th>
 *           </tr>
 *       </thead>
 *       <tbody>
 *
 *           <c:forEach var="o" items="${categoryForm.list}">
 *               <tr>
 *               	 // tdのclassは必ず指定すること
 *                   // classは、addNew.jsp, edit.jsp でapplyArrayで指定するのに必要。
 *                   <td class="categoryId">
 *                   	<c:out value="${o.categoryId}" />
 *                   	<input type="hidden" class="hid-dataTable-keyId" value="<c:out value="${o.categoryId}" />" disabled="disabled" />
 *                   </td>
 *                   <td class="categoryName">
 *                   	<c:out value="${o.categoryName}" />
 *                   </td>
 *               </tr>
 *           </c:forEach>
 *
 *       </tbody>
 *   </table>
 *                                     :
 *                                     :
 *   // js
 *   // 【注意】 addNew.jsp, edit.jsp のダイアログ内でも使うので、「$(function() ...)」の外で定義すること。
 *   var dtAjaxEditor =
 *   	new DtAjaxEditor(
 *   		'#modal-area',
 *   		'#dataTable',
 *   		{ stateSave:true, pageSave:true, emptyMsg:'研修受講カテゴリは1件もありません' },
 *   	   	'<%=request.getContextPath()%>/pm/training/category/edit-s.do',
 *   	   	'<%=request.getContextPath()%>/pm/training/category/delete-s.do'
 *   	);
 *
 *   $(function() {
 *
 *       dtAjaxEditor.clickEventAddNew(
 *       	'<%=request.getContextPath()%>/pm/training/category/addNew-s.do',
 *       	$('#btn-addNew')
 *       );
 *
 *   });
 *
 * ************************************************************************************
 *   ○ addNew.jsp
 *       ・ closeAjaxEditorのhiddenを用意すること。(入力エラーの場合はfalseを返すようにしておく)
 *       ・ hid-edit-keyIdのhiddenを用意すること。
 * ************************************************************************************
 *   // html
 *   <html-el:hidden property="closeAjaxEditor" value="${categoryForm.closeAjaxEditor}" />
 *                                     :
 *                                     :
 *   <html:form action="/categoryLookupDispatch" styleId="modal-addNew-form">
 *                                     :
 *                                     :
 *   <table>
 *       <tr>
 *           <th style="width: 100px;">コード<%= Html.markRequired() %></th>
 *           <td>
 *               <html-el:text property="item.categoryId"
 *                             value="${categoryForm.item.categoryId}"
 *                             maxlength="4"
 *                             styleClass="asciiInput text-size-4 target-focus"
 *                             styleId="txt-category-id"
 *                             />
 *               <html:errors  property="item.categoryId"/>
 *           </td>
 *       </tr>
 *       <tr>
 *           <th>名称<%= Html.markRequired() %></th>
 *           <td>
 *               <html-el:text property="item.categoryName"
 *                             value="${categoryForm.item.categoryName}"
 *                             maxlength="50"
 *                             styleClass="stringInput text-size-50"
 *                             styleId="txt-category-name"
 *                             />
 *               <html:errors  property="item.categoryName"/>
 *           </td>
 *       </tr>
 *   </table>
 *                                     :
 *                                     :
 *   // js
 *   dtAjaxEditor.submitEventCreate(
 *   		'modal-addNew-form',
 *   		'txt-category-id',
 *   	    {
 *                  // [list.jsp] td.className : [addNew.jsp] input.id
 *  	    		categoryId: '#txt-category-id',
 *  	       		categoryName: '#txt-category-name'
 *   	    }
 *   );
 *
 * ************************************************************************************
 *   ○ edit.jsp
 *       ・ closeAjaxEditorのhiddenを用意すること。(入力エラーの場合はfalseを返すようにしておく)
 *       ・ hid-edit-keyIdのhiddenを用意すること。
 * ************************************************************************************
 *   // html
 *   <html-el:hidden property="closeAjaxEditor" value="${categoryForm.closeAjaxEditor}" />
 *                                     :
 *                                     :
 *   <html:form action="/categoryLookupDispatch" styleId="modal-edit-form">
 *                                     :
 *                                     :
 *   <table>
 *       <tr>
 *           <th style="width: 100px;">コード</th>
 *           <td>
 *           	<c:out value="${categoryForm.item.categoryId}" />
 *           	<input type="hidden" id="hid-edit-keyId" value="<c:out value="${categoryForm.item.categoryId}" />" disabled="disabled" />
 *           </td>
 *       </tr>
 *       <tr>
 *           <th>名称<%= Html.markRequired() %></th>
 *           <td>
 *               <html-el:text property="item.categoryName"
 *                             value="${categoryForm.item.categoryName}"
 *                             maxlength="50"
 *                             styleClass="stringInput text-size-40"
 *                             styleId="txt-category-name"
 *                             />
 *               <html:errors  property="item.categoryName"/>
 *           </td>
 *       </tr>
 *    </table>
 *                                     :
 *                                     :
 *   // js
 *   dtAjaxEditor.submitEventEdit(
 *   		'modal-edit-form',
 *   		'hid-edit-keyId',
 *   		{
 *       		categoryName: '#txt-category-name'
 *       	}
 *   );
 *
 *
 * @param dialogId     ダイアログId (例) #modal-area
 * @param dataTableId  DataTable形式で表示する元テーブルのId (例) #dataTable
 * @param optDataTable DataTableのオプション指定
 * @param urlEdit      編集リンクをクリックしたときにEditActionを呼び出すURL
 * @param urlDelete    削除リンクをクリックしたときにDeleteActionを呼び出すURL
 * @param argWidth     ダイアログの幅を指定する[初期値=600]
 * @returns
 */
function DtAjaxEditor (dialogId, dataTableId, optDataTable, urlEdit, urlDelete, argWidth) {

    /***********************************************************************************
     * プロパティ
    ***********************************************************************************/
    this._dialogId  = dialogId;
    this._dataTable = null;
    this._urlEdit   = urlEdit;
    this._urlDelete = urlDelete;

    var c = $(dataTableId).find('thead > tr > th').length; // th要素数を取得
    this._colspan   = c + 1; // 「編集 | 削除」列分を追加

    if (!argWidth) {
        argWidth = 600;
    }

    // Dialogオプション
    this._optDialog = {
            autoOpen: false,
            modal: true,
            resizable: true,
            width: argWidth,
            position: { my: "top+30", at: "center top", of: window },
            show: function() {$(this).fadeIn(300);}
    };

    this._hiddenStateName          = 'closeAjaxEditor';
    this._listKeyIdHiddenClassName = 'hid-dataTable-keyId';
    this._linkEditClassName        = 'link-edit';
    this._linkDeleteClassName      = 'link-del';
    this._linkEditWord             = '編集';
    this._linkWordSeparate         = ' | ';
    this._linkDeleteWord           = '削除';
    this._titleAddNew              = '新規入力';
    this._titleEdit                = '編集入力';
    this._highlightColor           = '#ffff91';
    this._deleteAlertMessage       = '削除が選択されました．\n続行しますか？';

    /***********************************************************************************
     * private
     * ※コンストラクタ内でもprivate関数を使用するため、先に最初に定義する。
    ***********************************************************************************/
    /**
     * 背景色ハイライト設定
     *
     * @param td tdオブジェクト
     */
    this._setHighlight = function (td) {

        var p = this;

        td.css({ backgroundColor: p._highlightColor })
          .stop().animate(
                  {
                      backgroundColor: '#fff'
                  },
                  {
                      duration: 1500,
                      complete: function() {
                          // ハイライト完了後に元の背景色に戻す
                          td.css('backgroundColor', '');
                      }
                  });
    }

    /**
     * Ajax通信失敗時のイベント
     */
    this._ajaxErrorEvent = function () {
        alert('サーバが込み合っています。時間を置いて実行してください');
    }

    /**
     * リンクTdを作成
     * 「編集 | 削除」
     *
     * @param tr trオブジェクト
     * @param keyId 行の一意ID。編集ダイアログ表示や、データ削除することき使用するキー。
     */
    this._createLinkTd = function (tr, keyId) {

        var p = this;

        // --------------------------------
        // 編集リンクを作成
        // --------------------------------
        var linkEdit =
            $('<a/>',
                {
                    href:    '#',
                    'class': p._linkEditClassName,
                    style:   'white-space: nowrap'
                }
            ).html(p._linkEditWord);

        linkEdit.click(function() {

            $(p._dialogId).html('');

            $.ajax({
                url: p._urlEdit,
                data: {
                    ajaxEditorKeyId: keyId
                },
                dataType: 'html',
                cache: false,
                success: function(data, status, xhr) {
                    $(p._dialogId).html(data);
                },
                error: function() {
                    p._ajaxErrorEvent();
                }
            });

            p._optDialog['title'] = p._titleEdit;

            $(p._dialogId)
                .dialog(p._optDialog)
                .dialog( 'open' );

            return false;
        });

        // --------------------------------
        // 削除リンクを作成
        // --------------------------------
        var linkDelete =
            $('<a/>',
                {
                    href: '#',
                    'class': p._linkDeleteClassName,
                    style: 'white-space: nowrap'
                }
            ).html(p._linkDeleteWord);

        linkDelete.click(function() {

            if ( !confirm( p._deleteAlertMessage ) ){
                return false;
            }

            var link = $(this);
            var tr   = link.parent().parent();

            // loader 削除
            tr.find('td').remove();
            $('.reddog-ajax-loading').remove();

            // loader 追加
            var td = $('<td />', { colspan: p._colspan, style: 'text-align: center;' });
            td.append('<span class="reddog-ajax-loading">&nbsp;</span>');
            tr.html(td);

            $.getJSON(
                    p._urlDelete,
                    {
                        ajaxEditorKeyId: keyId
                    },
                    function(data){

                        // 結果メッセージ表示
                        td.html(data.view_msg);
                        tr.html(td);

                        // ハイライト設定
                        p._setHighlight( td );

                    }
                    ).error(function() {
                        p._ajaxErrorEvent();
                    }
                    ).complete(function() {
                        $('.reddog-ajax-loading').remove();
                    }
                );

            return false;
        });

        // --------------------------------
        // Td作成
        // --------------------------------
        var newTdLink =
            $('<td/>')
                .append(linkEdit)
                .append(p._linkWordSeparate)
                .append(linkDelete);

        tr.append(newTdLink);
    }

    /**
     * ダイアログ閉じる時に初期化を行う
     *  ・ 2回以上ダイアログを開くと、ダイアログ内のDatePickerが動かなくなる現象があるため、
     *    ダイアログを閉じるときにDatePickerを削除する。
     */
    this._closeEventInit = function () {

        // DatePicker初期化
        $('div[aria-describedby=modal-area]').find('.ui-dialog-titlebar-close').click(function() {

            var datePickerId = '#ui-datepicker-div';
            if (!$(datePickerId).exists()) { return; }

            $(datePickerId).remove();
        });
    }

    /**
     * Datepickerを初期化する
     */
    this._removeDatepicker = function () {

        var datePickerId = '#ui-datepicker-div';
        if (!$(datePickerId).exists()) { return; }

        $(datePickerId).remove();
    }

    /**
     * 一覧の行へ当てはめるための指定文字列を配列に変換する
     *
     * 例)
     * "loginId: #txt-loginId | userName: #txt-userName | adminFlg: #txt-adminFlg"
     *   ↓
     * {loginId: '#txt-loginId', userName: '#txt-userName', adminFlg: '#txt-adminFlg'}
     */
    this._applies2applyArray = function (applies) {

        var applyArray = {};

        var sp1 = applies.split('|');

        $.each(sp1, function(i) {
            var tmp = jQuery.trim(sp1[i]);
            var sp2 = tmp.split(':');
            var key = jQuery.trim(sp2[0]);

            // sp2[1]に「:」が入っていると、さらに配列ができてしまうため、sp2[0]を省いて値だけを取得する
            var val = jQuery.trim(tmp.replace(sp2[0]+':', ''));

            applyArray[key] = val;
        });

        return applyArray;
    }

    /**
     * 要素の値を取得して返す。
     * 要素の種類によって値取得方法が異なる
     *
     * ■チェックボックスの場合
     *   #id[trueの表示文字,falseの表示文字]
     *   で指定する。
     *   例)
     *     指定文字： #chk-adminFlg[管理者,一般]
     *     値:true
     *       ↓
     *     返り値：管理者
     *
     * ■selectの場合
     *   #id[値1=名称1,値2=名称2...]
     *   で指定する。
     *   例)
     *     指定文字： #sel-department[001001=営業１課,001002=営業２課]
     *     値:001001
     *       ↓
     *     返り値：営業１課
     *
     * ■radioの場合
     *   .rdo-className[値1=名称1,値2=名称2...]
     *   で指定する。
     *   例)
     *     指定文字： .rdo-sex[1=男,2=女]
     *     値:1
     *       ↓
     *     返り値：男
     *
     * ■上記以外の場合
     *   $('#id').val()
     *   で返す。
     */
    this._getElementValue = function (selector) {
        var re = '';

        // []部分を除去して要素を特定する
        var target = $(selector.replace(/\[\S*\]/, ''));

        if (!target.exists()) {
            return re;
        }

        // --------------------------
        // checkbox
        // --------------------------
        if (target.get(0).tagName == 'INPUT'
                && target.attr('type') == 'checkbox') {

            // #id[trueの表示文字,falseの表示文字] 形式であるかチェック
            var regex = /^(#|\.)[a-zA-Z\-]+\[\S*\,\S*\]$/;
            if (regex.test(selector)) {

                // 呼び元で指定されたtrue,falseの表示文字列を抜き出す
                var mt  = selector.match(/\[\S*\,\S*\]$/);
                var tmp = mt[0].replace(/^\[/, '').replace(/\]$/, '');
                var sp  = tmp.split(',');
                var trueDisp  = sp[0];
                var falseDisp = sp[1];

                re = (target.prop('checked'))? trueDisp: falseDisp;
            }
        }

        // --------------------------
        // select
        // --------------------------
        else if (target.get(0).tagName == 'SELECT') {

            // #id[値1=名称1,値2=名称2...] 形式であるかチェック
            var regex = /^(#|\.)[a-zA-Z\-]+\[(\d+=[^=^,]*)(,\d+=[^=^,]*)*\]$/;
            if (regex.test(selector)) {

                // 呼び元で指定された「値=名称」を抜き出す
                var mt  = selector.match(/\[(\d+=[^=^,]*)(,\d+=[^=^,]*)*\]$/);
                var tmp = mt[0].replace(/^\[/, '').replace(/\]$/, '');
                var sp  = tmp.split(',');

                $.each(sp, function(i, val) {
                    var kv = val.trim().split('=');

                    if (kv[0] == target.val()) {
                        re = kv[1];
                        return;
                    }
                });
            }
        }

        // --------------------------
        // radio
        // --------------------------
        else if (target.get(0).tagName == 'INPUT'
                    && target.attr('type') == 'radio') {

            // #id[値1=名称1,値2=名称2...] 形式であるかチェック
            var regex = /^(#|\.)[a-zA-Z\-]+\[(\d+=[^=^,]*)(,\d+=[^=^,]*)*\]$/;
            if (regex.test(selector)) {

                // 呼び元で指定された「値=名称」を抜き出す
                var mt  = selector.match(/\[(\d+=[^=^,]*)(,\d+=[^=^,]*)*\]$/);
                var tmp = mt[0].replace(/^\[/, '').replace(/\]$/, '');
                var sp  = tmp.split(',');

                $.each(sp, function(i, val) {
                    var kv = val.trim().split('=');

                    if (kv[0] == target.filter(":checked").val()) {
                        re = kv[1];
                        return;
                    }
                });
            }
        }

        // --------------------------
        // 他の要素は値そのまま
        // --------------------------
        else {
            re = target.val();
        }

        return re;
    }

    /***********************************************************************************
     * コンストラクタ
    ***********************************************************************************/
    var dt = $(dataTableId);
    var p  = this;

    // リンク列を作成 「編集 | 削除」
    dt.find('thead tr').append('<th>&nbsp;</th>');

    dt.find('tbody tr').each(function() {

        var tr = $(this);
        var keyId = tr.find('.'+ p._listKeyIdHiddenClassName).val();

        p._createLinkTd(tr, keyId);
    });

    // DataTable作成
    this._dataTable = dt.dataTablesJP( optDataTable );

    /***********************************************************************************
     * public
    ***********************************************************************************/
    /**
     * 新規入力表示 クリックイベント作成
     *
     * @param url AddNewActionを呼び出すURL。
     * @param clickTarget
     *        新規入力ダイアログを表示するためのオブジェクトを指定。
     *        ボタン or リンク。
     *        ex) $('#btn-addNew')
     */
    this.clickEventAddNew = function (url, clickTarget) {

        var p = this;

        // --------------------------------
        clickTarget.click(function() {

            $(p._dialogId).html('');

            $.ajax({
                url: url,
                dataType: 'html',
                cache: false,
                success: function(data, status, xhr) {
                    $(p._dialogId).html(data);
                },
                error: function() {
                    p._ajaxErrorEvent();
                }
            });

            p._optDialog['title'] = p._titleAddNew;

            $(p._dialogId)
                .dialog(p._optDialog)
                .dialog('open');

            return false;
        });

    }

    /**
     * 新規作成ポップアップ Submitイベント作成
     *
     * @param formId formのid
     * @param textKeyId キーとなる値
     * @param applies
     * 	      入力した値をテーブルに入れるための文字列。
     *        "(list.jspのtd.className) : (edit.jspのinputなどのid) | (list.jspのtd.className) : (edit.jspのinputなどのid) ..." で設定する。
     *        ２つ目以降はパイプ区切り
     */
    this.submitEventCreate = function(formId, textKeyId, applies) {

        var p = this;

        // appliesを連想配列に変換
        var applyArray = this._applies2applyArray(applies);

        p._closeEventInit();

        // --------------------------------
        $('#'+ formId).submit(function(event) {
            event.preventDefault();

            // formパラメータの後で実行すること
            $(p._dialogId).parents('.ui-dialog').hide();

            // datepicker初期化
            p._removeDatepicker();

            var f = $(this);

            $.ajax({
                url: f.attr('action'),
                type: f.attr('method'),
                data: f.serialize(),
                cache: false,
                success: function(data, status, xhr) {
                    $(p._dialogId).html(data);

                    var newKeyId = $('#'+ textKeyId).val();

                    var state = $('input[name='+ p._hiddenStateName+ ']').val();

                    if (state != 'true') {
                        $(p._dialogId).parents('.ui-dialog').show();
                        return;
                    }

                    // Hiddenキーを作成
                    var hiddenKeyId =
                        $('<input/>', {
                            type:     'hidden',
                            'class':  p._listKeyIdHiddenClassName,
                            value:    newKeyId,
                            disabled: 'disabled'
                        });

                    // Trを作成
                    var newTr = $('<tr/>');

                    var firstTd = true;

                    for (var key in applyArray) {

                        var selector = applyArray[key];

                        var val = p._getElementValue(selector);

                        var newTd  = $('<td/>', { 'class': key }).html(val);

                        // 1番目のTdにHiddenキーを埋め込む
                        if (firstTd) {
                            newTd.append( hiddenKeyId );
                            firstTd = false;
                        }

                        newTr.append(newTd);
                    }

                    // 「編集 | 削除」リンクTdを追加
                    p._createLinkTd(newTr, newKeyId);

                    // ハイライト設定
                    p._setHighlight( newTr.children('td') );

                    var tbl = p._dataTable.dataTable();

                    // データ追加
                    tbl.find('tbody').prepend(newTr); // 表の1行目に追加
                    tbl.fnAddTr(newTr[0], /*bRedraw=*/false); // 裏データを追加

                    // ダイアログを閉じる
                    $(".ui-dialog-titlebar-close").trigger('click');
                },
                error: function() {
                    p._ajaxErrorEvent();
                }
            });

            return false;
        });

    }

    /**
     * 編集ポップアップ Submitイベント作成
     *
     * @param formId formのid
     * @param hiddenKeyId キーとなる値
     * @param applies
     * 	      入力した値をテーブルに入れるための文字列。
     *        "(list.jspのtd.className) : (edit.jspのinputなどのid) | (list.jspのtd.className) : (edit.jspのinputなどのid) ..." で設定する。
     *        ２つ目以降はパイプ区切り
     */
    this.submitEventEdit = function(formId, hiddenKeyId, applies) {

        var p = this;

        // appliesを連想配列に変換
        var applyArray = this._applies2applyArray(applies);

        p._closeEventInit();

        // --------------------------------
        $('#'+ formId).submit(function(event) {
            event.preventDefault();

            // formパラメータの後で実行すること
            $(p._dialogId).parents('.ui-dialog').hide();

            // datepicker初期化
            p._removeDatepicker();

            var f = $(this);

            $.ajax({
                url: f.attr('action'),
                type: f.attr('method'),
                data: f.serialize(),
                cache: false,
                success: function(data, status, xhr) {
                    $(p._dialogId).html(data);

                    var state = $('input[name='+ p._hiddenStateName+ ']').val();

                    if (state != 'true') {
                        $(p._dialogId).parents('.ui-dialog').show();
                        return;
                    }

                    var keyId = $('#'+ hiddenKeyId).val();

                    p._dataTable.find('.'+ p._listKeyIdHiddenClassName).each(function() {

                        if (keyId == $(this).val()) {

                            var tr = $(this).closest('tr');

                            // ハイライト設定
                            p._setHighlight( tr.children('td') );

                            // Hiddenキーを作成
                            var hiddenKeyId =
                                $('<input/>', {
                                    type:     'hidden',
                                    'class':  p._listKeyIdHiddenClassName,
                                    value:    keyId,
                                    disabled: 'disabled'
                                });

                            var firstTd = true;

                            // Td作成
                            for (var key in applyArray) {

                                var selector = applyArray[key];

                                var val = p._getElementValue(selector);

                                tr.children('td.'+ key).html( val );

                                // 1番目のTdにHiddenキーを埋め込む
                                if (firstTd) {
                                    tr.children('td.'+ key).append( hiddenKeyId );
                                    firstTd = false;
                                }
                            }
                        }
                    });

                    // ダイアログを閉じる
                    $(".ui-dialog-titlebar-close").trigger('click');
                },
                error: function() {
                    p._ajaxErrorEvent();
                }
            });

            return false;
        });
    }
}
