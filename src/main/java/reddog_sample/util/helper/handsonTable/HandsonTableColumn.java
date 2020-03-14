package reddog_sample.util.helper.handsonTable;

import java.util.ArrayList;
import java.util.List;

import org.seasar.framework.util.StringUtil;

/**
 * HandsonTableに表示する列の定義
 */
public class HandsonTableColumn {

    // Cell type (コメントアウトは未実装)
    public static final String TYPE_NUMERIC      = "numeric";
    //public static final String TYPE_DATE         = "date";
    //public static final String TYPE_CHECKBOX     = "checkbox";
    //public static final String TYPE_SELECT       = "select";
    public static final String TYPE_DROPDOWN     = "dropdown";
    public static final String TYPE_AUTOCOMPLETE = "autocomplete";
    //public static final String TYPE_PASSWORD     = "password";
    //public static final String TYPE_HANDSONTABLE = "handsontable";

    // フォーマット定数 http://numeraljs.com/
    public static final String FORMAT_NUMBER = "0,0[.][00000000]"; // 通常
    //public static final String FORMAT_PERCENT = "0[.][00000000]%"; // パーセント
    //public static final String FORMAT_SCALE_2 = "0,0.00"; // 小数点第2位まで表示

    /**
     * コンストラクタ
     * @param headerName ヘッダー名
     * @param data データ名
     */
    public HandsonTableColumn(String headerName, String data) {
        _headerName = headerName;
        _data = data;
    }

    // ------------------------------------------------------

    private String _headerName;
    private String _data;

    private String _type          = "";
    private String _format        = "";
    private List<String> _source  = new ArrayList<String>();
    private boolean _strict       = false;
    private boolean _allowInvalid = true;
    private boolean _readOnly     = false;
    private int _colWidth         = 0;

    // ------------------------------------------------------
    /**
     * 列名を取得
     */
    public String getHeaderName() {
        return _headerName;
    }

    /**
     * データ名を取得
     */
    public String getData() {
        return _data;
    }

    /**
     * セル種別を取得
     */
    public String getType() {
        return _type;
    }
    /**
     * セル種別をセット
     */
    public void setType(String type) {
        this._type = type;
    }

    /**
     * フォーマットを取得
     */
    public String getFormat() {
        return _format;
    }
    /**
     * フォーマットをセット
     */
    public void setFormat(String format) {
        this._format = format;
    }

    /**
     * 選択肢配列を取得
     * <p>
     * ※typeがdropdown, autocomplete時のみ有効
     */
    public List<String> getSource() {
        return _source;
    }
    /**
     * 選択肢を「key：value」形式に変換して追加する
     *
     * @param key キー名称
     * @param value 値
     */
    public void addSource(Object key, String value) {

        if (key == null || StringUtil.isEmpty(value)) {
            _source.add("''");

        } else {
            _source.add(String.format("'"+ HandsonTableBuilder.FORMAT_MASTER+ "'", String.valueOf(key), value));
        }
    }

    /**
     * 選択肢以外の文字列の禁止
     * <p>
     * ※typeがautocomplete時のみ有効<br>
     * 禁止の場合、選択肢以外の文字列は入力できず、セル背景色を赤になる。
     *
     * @return true: 禁止する, false: しない
     */
    public boolean isStrict() {
        return _strict;
    }
    /**
     * 選択肢以外の文字列の禁止
     *
     * @param strict true: 禁止する, false: しない
     */
    public void setStrict(boolean strict) {
        _strict = strict;
    }

    /**
     * 選択肢以外の文字列を許可
     * <p>
     * ※typeがdropdown時のみ有効
     *
     * @return true: 許可する, false: しない
     */
    public boolean isAllowInvalid() {
        return _allowInvalid;
    }
    /**
     * 選択肢以外の文字列を許可
     *
     * @param allowInvalid true: 許可する, false: しない
     */
    public void setAllowInvalid(boolean allowInvalid) {
        _allowInvalid = allowInvalid;
    }

    /**
     * 読み取り専用 [初期値： true]
     */
    public boolean isReadOnly() {
        return _readOnly;
    }
    /**
     * 読み取り専用
     *
     * @param readOnly 読み取り専用にtrue: する、false: しない
     */
    public void setReadOnly(boolean readOnly) {
        _readOnly = readOnly;
    }

    /**
     * 列幅 [初期値: 0](=指定しない)
     */
    public int getColWidth() {
        return _colWidth;
    }
    /**
     * 列幅
     */
    public void setColWidth(int colWidth) {
        _colWidth = colWidth;
    }
}
