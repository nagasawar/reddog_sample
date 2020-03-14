package reddog_sample.util.helper.handsonTable;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import reddog_sample.util.helper.Html;

/**
 * HandsonTableのスクリプトを作成するクラス。
 * <p>
 * 作成にはHandsonTableColumnクラス, HandsonTableDataクラスが必要。<br>
 */
public class HandsonTableBuilder {

    /**
     * HandsonTable表示先、div本体のId
     */
    private String _divId = "reddog-handsonTable";

    // ---------------------------------------------
    /**
     * マスタ形式文字列
     * <p>
     * HandsonTableでは、HTMLのselectのように値をvalue, labelで持つことができない。<br>
     * そのため、「value：label」で値を持たせる。
     */
    public static final String FORMAT_MASTER = "%s：%s";

    /**
     * マスタ形式正規表現
     */
    public static final String REGEX_MASTER = "^[0-9]+：";

    /**
     * マスタ形式区切り文字
     */
    public static final String MASTER_SEPARATOR = "：";

    // ---------------------------------------------

    private String _cssTag;
    private String _jsTag;
    private String _hiddenId;

    /**
     * コンストラクタ
     * <p>
     * submit時にHandsonTableのJSON値を格納するhidden要素を指定する。<br>
     * ついでに必要なcss, javascriptファイルの読込タグを変数へ格納しておく。
     *
     * @param request
     * @param hiddenId submit時にHandsonTableのJSON値を格納するhidden要素のId
     */
    public HandsonTableBuilder(HttpServletRequest request, String hiddenId) {

        _cssTag = Html.linkTagWriter(request, "package/handsontable/css/jquery.handsontable.full.css");
        _jsTag  = Html.scriptTagWriter(request, "package/handsontable/js/jquery.handsontable.full.js");

        _hiddenId = hiddenId;
    }

    // ---------------------------------------------

    /**
     * 表示列定義リスト
     */
    private List<HandsonTableColumn> _htColumns = new ArrayList<HandsonTableColumn>();

    /**
     * 表示列定義リストへ追加
     *
     * @param htColumn HandsonTableColumnクラス
     */
    public void addHtColumn(HandsonTableColumn htColumn) {
        _htColumns.add(htColumn);
    }

    /**
     * HandsonTableDataクラスのsetHtColumnsを実行して表示列定義をセット
     *
     * @param htDataClass
     * @return HandsonTableBuilder チェーンメソッドのため
     */
    public HandsonTableBuilder setHtColumns(Class<? extends HandsonTableData> htDataClass)
            throws InstantiationException, IllegalAccessException {

        HandsonTableData hd = htDataClass.newInstance();
        hd.setHtColumns(this);
        return this;
    }

    /**
     * データリスト
     */
    private List<HandsonTableData> _htDatas = new ArrayList<HandsonTableData>();

    /**
     * データリストを取得
     */
    public List<HandsonTableData> getHtDatas() {
        return _htDatas;
    }

    /**
     * データリストをセット
     */
    public void setHtDatas(List<HandsonTableData> htDatas) {
        _htDatas = htDatas;
    }

    /**
     * モデルリストをデータリストへセット
     * <p>
     * activeJdbcモデルクラスのリストをHandsonTableDataクラスへ格納する。<br>
     *
     * @param htDataClass HandsonTableDataクラスの実装クラス
     * @param modelList activeJdbcのモデルリスト
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void setHtDatas(Class<? extends HandsonTableData> htDataClass, List<?> modelList)
            throws InstantiationException, IllegalAccessException {

        for (Object obj : modelList) {

            HandsonTableData hd = htDataClass.newInstance();
            hd.setProperties(obj);

            _htDatas.add(hd);
        }
    }

    /**
     * JSON文字列をデータリストへセット
     * <p>
     * JSON文字列をObjectクラスへ変換し、HandsonTableDataクラスへ格納する。<br>
     *
     * @param htDataClass HandsonTableDataクラスの実装クラス
     * @param jsonStr JSON文字列
     * @throws ParseException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void setHtDatas(Class<? extends HandsonTableData> htDataClass, String jsonStr)
            throws ParseException, InstantiationException, IllegalAccessException {

        JSONParser parser = new JSONParser();
        JSONArray list = (JSONArray) parser.parse(jsonStr);

        List<HandsonTableData> htDatas = new ArrayList<HandsonTableData>();

        for (Object o : list) {

            JSONObject json = (JSONObject)o;

            HandsonTableData hd = htDataClass.newInstance();
            hd.setProperties(json);

            htDatas.add(hd);
        }

        _htDatas = htDatas;
    }

    /**
     * エラーメッセージ
     */
    String _errMsg = "";

    /**
     * 入力チェック
     * <p>
     * HandsonTableDataクラスリストを取りまとめて入力チェックを行う。<br>
     * 入力チェック内容はHandsonTableDataの実装クラスの『validCoreメソッド』にて定義する。<br>
     * rowで行番号を管理する。
     */
    public void validHtDatas() {

        _errMsg = "";

        int row = 1;
        for (HandsonTableData hd : _htDatas) {
            _errMsg += hd.validate(row);
            row++;
        }
    }

    /**
     * エラーがあるかチェックする
     *
     * @return true: エラー有り、false: エラー無し
     */
    public boolean isInValid() {
        return (_errMsg.length() > 0);
    }

    /**
     * エラーが無いかチェックする
     *
     * @return true: エラー無し、false: エラー有り
     */
    private boolean isValid() {
        return (_errMsg.length() == 0);
    }

    /**
     * 保存
     * <p>
     * HandsonTableDataクラスリストを取りまとめて保存を行う。<br>
     */
    public void saveHtDatas() {

        for (HandsonTableData hd : _htDatas) {
            hd.save();
        }
    }

    // ---------------------------------------------

    private String _changeEvent = "null";

    /**
     * セル内容変更時のイベントを定義する
     * <p>
     * <pre>{@code
     * String event = "";
     *
     * event += "function(change, source) {";
     * event += "    if(source === 'loadData') return;";
     *
     * event += "    $.each(change, function(i, value) {";
     *
     * event += "        var field = value[1]; // 変更フィールド名の取得";
     * event += "        var row   = value[0]; // 変更行数の取得";
     * event += "        var value = value[3]; // 変更後のデータ取得";
     *
     * event += "        data[row][field] = value;";
     *
     * event += "        var ex   = data[row]['ex'];   // 変更行の数量取得";
     * event += "        var tank = data[row]['tank']; // 変更行の単価取得";
     * event += "        var kin  = ex * tank;";
     *
     * event += "        if(isFinite(kin)) {";
     * event += "            data[row]['kin'] = kin;";
     * event += "        } else {";
     * event += "            data[row]['kin'] = '';";
     * event += "        }";
     * event += "    });";
     *
     * event += "    container.handsontable('render');";
     * event += "}";
     *
     * htBuilder.setChangeEvent(event);
     * </pre>
     */
    public void setChangeEvent(String changeEvent) {
        _changeEvent = changeEvent;
    }


    private boolean _useScrollbarH = false;
    private String _scrollbarHeight = "";

    /**
     * 縦に対してのスクロールバーを表示する
     *
     * @param scrollbarHeight 高さ  ex) 500px
     */
    public void useScrollbarH(String scrollbarHeight) {

        this._useScrollbarH = true;
        this._scrollbarHeight = scrollbarHeight;
    }

    /**
     * [Overload] 縦に対してスのクロールバーを表示する
     * <p>
     * 高さの指定がない場合は、300pxとする
     */
    public void useScrollbarH() {
        this.useScrollbarH("300px");
    }

    // ---------------------------------------------
    /**
     * HandsonTableスクリプトを出力する
     */
    @Override
    public String toString() {

        // ---------------------------------------------------------------
        // 列数が正しいかチェック
        // ---------------------------------------------------------------
        _checkColumsAndDatas();

        // ---------------------------------------------------------------
        // スクリプトタグ作成
        // ---------------------------------------------------------------
        StringBuilder b = new StringBuilder();

        b.append( _cssTag );
        b.append( _jsTag );

        b.append( this._getHtmlDataTag() );
        b.append( this._getHtmlErrMsg() );

        b.append("<script type=\"text/javascript\">\n");
        b.append("$(function() { \n");

        b.append( this._getJsColHeaders() );
        b.append( this._getJsData() );
        b.append( this._getJsErrorData() );
        b.append( this._getJsColWidths() );
        b.append( this._getJsColumns() );
        b.append( this._getJsErrorFunctions() );
        b.append( this._getJsChangeEvent() );
        b.append( this._getJsContainer() );
        b.append( this._getJsFormSubmitEvent() );

        //b.append("$('#"+ this._divId+ " .wtHider').css('width', '100%')");
        b.append("}); \n");
        b.append("</script>\n");

        return b.toString();
    }

    // ----------------------------------------------

    /**
     * 列定義とデータが正しいかチェック
     * <p>
     * HandsonTableColumnクラスで定義した列が、HandsonTableDataクラスにあるかチェックする
     */
    @SuppressWarnings("boxing")
    private void _checkColumsAndDatas() {

        // columnsに定義されている値が存在するかチェック
        for (HandsonTableColumn htCol : _htColumns) {

            for (HandsonTableData htData : _htDatas) {

                if (!htData.getJsonMap().containsKey(htCol.getData())) {
                    String msg = String.format("【開発者向け】HandsonTableColumnで定義した列がHandsonTableDataにありません(列名: %s, data名: %s)",
                                               htCol.getHeaderName(),
                                               htCol.getData());
                    throw new RuntimeException(msg);
                }
            }
        }

        // columnsに定義してる数のErrorが定義されているかチェック
        for (HandsonTableData htData : _htDatas) {

            if (_htColumns.size() != htData.getStrValidList().size()) {
                String msg = String.format("【開発者向け】HandsonTableColumnで定義した列数とHandsonTableDataのgetValidArrayの数が違います(colums: %s, array: %s)",
                                           _htColumns.size(),
                                           htData.getStrValidList().size());
                throw new RuntimeException(msg);
            }
        }
    }

    /**
     * Data表示用のHtmlタグを取得
     */
    private String _getHtmlDataTag() {

        String style = "";
        if (this._useScrollbarH) {
            style = String.format(" style=\"height: %s; overflow: auto;\"", this._scrollbarHeight);
        }

        return String.format("<div id=\"%s\"%s></div>", this._divId, style);
    }

    /**
     * エラーメッセージ表示用のHtmlタグを取得
     * <p>
     * メッセージを開閉するJavascriptは、jquery.reddog.func.jsのinitErrorSummaryを使用している
     */
    private String _getHtmlErrMsg() {

        if (this.isValid()) {
            return "";
        }

        String re = "";

        // エラーメッセージ本体
        re += "<div id=\"error-summary\" style=\"display: none;\"> \n";
        re += this._errMsg;
        re += "</div> \n";

        // 開閉スクリプト
        re += "<script type=\"text/javascript\"> \n";
        re += "$(function() { \n";
        re += "    $('#error-summary').reddog('initErrorSummary', { pattern: RD_ERRSUM_PATTERN_MID }); \n";
        re += "}); \n";
        re += "</script> \n";

        return re;
    }

    /**
     * ヘッダー名を取得
     */
    private String _getJsColHeaders() {
        String re = "";

        re += "function getColHeaders() { \n";
        re += "    return [ \n";

        boolean firstRow = true;

        for (HandsonTableColumn htcol : _htColumns) {

            if (firstRow) {
                firstRow = false;
            }
            else {
                re += ",";
            }

            re += String.format("'%s'", htcol.getHeaderName());
        }

        re += "    ];\n";
        re += "}\n";

        return re;
    }

    /**
     * 列定義を取得
     */
    @SuppressWarnings("boxing")
    private String _getJsColumns() {
        String re = "";

        re += "function getColumns() { \n";
        re += "    return [ \n";

        boolean firstRow = true;

        for (HandsonTableColumn htcol : _htColumns) {

            if (firstRow) {
                firstRow = false;
            }
            else {
                re += ",";
            }

            re += " { ";
            re += String.format("data: '%s',", htcol.getData());

            if (htcol.getType().length() > 0) {
                re += String.format("type: '%s',", htcol.getType());
            }
            if (htcol.getFormat().length() > 0) {
                re += String.format("format: '%s',", htcol.getFormat());
            }
            if (htcol.getSource().size() > 0) {
                re += String.format("source: %s,", htcol.getSource());
            }
            re += String.format("strict: %s,", htcol.isStrict());
            re += String.format("allowInvalid: %s,", htcol.isAllowInvalid());
            re += String.format("readOnly: %s", htcol.isReadOnly());
            re += " } \n";
        }

        re += "    ]; \n";
        re += "} \n";

        return re;
    }

    /**
     * データを取得
     * <p>
     * callbackをするときに「data」として使用するため、data配列変数とする
     */
    private String _getJsData() {
        String re = "";

        re += "var data = [ \n";

        boolean firstRow = true;

        for (HandsonTableData hd : _htDatas) {

            if (firstRow) {
                firstRow = false;
            }
            else {
                re += ",";
            }

            re += hd.getJsonString()+ "\n";
        }

        re += "]; \n";

        return re;
    }

    /**
     * データの入力チェック判定を取得
     */
    private String _getJsErrorData() {
        String re = "";

        re += "function getErrorData() { \n";
        re += "    return [ \n";

        boolean firstRow = true;

        for (HandsonTableData hd : _htDatas) {

            if (firstRow) {
                firstRow = false;
            }
            else {
                re += ",";
            }

            re += hd.getErrorData();
        }

        re += "    ]; \n";
        re += "} \n";

        return re;
    }

    /**
     * 列幅を取得
     */
    private String _getJsColWidths() {
        String re = "";

        re += "function getColWidths() { \n";
        re += "    return [ \n";

        boolean firstRow = true;

        for (HandsonTableColumn htcol : _htColumns) {

            if (firstRow) {
                firstRow = false;
            }
            else {
                re += ",";
            }

            int cw = htcol.getColWidth();
            if (cw > 0) {
                re += String.valueOf( htcol.getColWidth() );
            }
        }

        re += "    ]; \n";
        re += "} \n";

        return re;
    }

    /**
     * サーバー入力チェック用のエラー背景色定義を取得
     */
    private String _getJsErrorFunctions() {
        String re = "";

        // 背景色定義
        re += "var errBackgroundColor = '#ffc0cb'; \n";

        // dropdown, autocomplete
        re += "function errorAutocompleteRenderer(instance, td, row, col, prop, value, cellProperties) { \n";
        re += "    Handsontable.renderers.AutocompleteRenderer.apply(this, arguments); \n";
        re += "    td.style.background = errBackgroundColor; \n";
        re += "} \n";

        // 上記以外
        re += "function errorTextCellRenderer(instance, td, row, col, prop, value, cellProperties) { \n";
        re += "    Handsontable.TextCell.renderer.apply(this, arguments); \n";
        re += "    td.style.background = errBackgroundColor; \n";
        re += "} \n";

        return re;
    }

    /**
     * 変更イベント定義を取得
     */
    private String _getJsChangeEvent() {
        String re = "";

        re += "function getChangeEvent() { \n";
        re += "    return "+ _changeEvent;
        re += "} \n";

        return re;
    }

    /**
     * HandsonTable本体定義を取得
     */
    private String _getJsContainer() {
        String re = "";

        re += String.format("var divId = '#%s';\n", this._divId);

        re += "var container = $(divId); \n";
        re += "container.handsontable({ \n";
        re += "    colHeaders: getColHeaders(), \n";
        re += "    columns: getColumns(), \n";
        re += "    colWidths: getColWidths(), \n";
        re += "    data: data, \n";
        re += "    stretchH: 'all', \n";            // 横幅を親要素に合わせる
        re += "    manualColumnResize: true, \n";   // 列のサイズ変更可
        re += "    enterBeginsEditing: false, \n";  // Enter押したとき編集する:true, 編集せずそのまま次へ:false
        re += "    onChange: getChangeEvent(), \n"; // 変更イベント。各画面で個別に設定する。
        re += "    columnSorting: true, \n";        // ソートを有効にする(cookie保存はしていないため、画面を更新するとリセットされる)

        re += "    cells: function (row, col, prop) { \n";

        re += "        if (getErrorData()[row][col]) { \n"; // エラーがあれば、背景色を変更してセルを表示する
        re += "            var type = getColumns()[col]['type']; \n";
        re += "            if (type == 'dropdown' || type == 'autocomplete') { \n";
        re += "                this.renderer = errorAutocompleteRenderer; \n";
        re += "            } \n";
        re += "            else { \n";
        re += "                this.renderer = errorTextCellRenderer; \n";
        re += "            } \n"; // 未実装... numeric,date,checkbox,select,password
        re += "        } \n";

        re += "    } \n";

        re += "});\n";

        // スクロールバー表示時は縦のリサイズができるようにする
        if (this._useScrollbarH) {
            re += "container.resizable({ \n";
            re += "    minHeight: 50, \n";     // 最少の高さ
            re += "    handles: 's' \n";       // 縦だけリサイズ
            re += "}); \n";
        }

        // containerのサイズに幅を合わせる(幅を合わせたいdivのclassに「ht-container」を設定しておく)
        re += "$(window).on('load resize', function(){ \n";
        re += "    var htContainer = $('div.ht-container'); \n";

        re += "    if (htContainer.exists()) { \n";
        re += "        var ht = container.handsontable('getInstance'); \n";
        re += "        ht.updateSettings({ width: htContainer.width() }); \n";
        re += "    } \n";

        re += "}); \n";

        return re;
    }

    /**
     * Submitイベント定義を取得
     */
    private String _getJsFormSubmitEvent() {
        String re = "";

        re += String.format("var hiddenId = '#%s';\n", this._hiddenId);

        // submitイベント定義(JSONを文字列に変換し、hiddenへセットする)
        re += "$('form').submit(function() { \n";
        re += "    var handsonTable = container.data('handsontable'); \n";
        re += "    var jsonStr = JSON.stringify(handsonTable.getData()); \n";
        re += "    $(hiddenId).val(jsonStr); \n";
        re += "}); \n";

        return re;
    }
}
