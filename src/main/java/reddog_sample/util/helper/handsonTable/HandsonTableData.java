package reddog_sample.util.helper.handsonTable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.seasar.framework.util.StringUtil;

/**
 * HandsonTableに表示するデータ部分を管理するクラス。
 * <p>
 * 使用するときは、このクラスのサブクラスを作成し、データとして使用するプロパティを定義する。<br>
 */
public abstract class HandsonTableData {

    /**
     * HandsonTableBuilderに列定義をする
     * <p>
     * (HandsonTableDataの中にColumn定義を持つのは微妙だが、
     * DataクラスのJSON_KEY定義が同じでなければならないので、Dataクラス内に定義させることとした)
     * <p>
     * 実装例：
     * <pre>{@code
     * public void setHtColumns(HandsonTableBuilder htBuilder) {
     *
     *     HandsonTableColumn hc;
     *
     *     // 支給月
     *     hc = new HandsonTableColumn("支給月", JSON_KEY_SUPYM);
     *     hc.setReadOnly(true);
     *     htBuilder.addHtColumn(hc);
     *
     *     // 所属
     *     hc = new HandsonTableColumn("所属", JSON_KEY_BELONG);
     *     hc.setType(HandsonTableColumn.TYPE_DROPDOWN);
     *     hc.setAllowInvalid(false);
     *
     *     hc.addSource("", "");
     *     for (SimMtBelong o : SimMtBelong.findAll()) {
     *         hc.addSource(o.getBelongId(), o.getBelongName());
     *     }
     *
     *     hc.setStrict(true);
     *     htBuilder.addHtColumn(hc);
     *
     *                :
     *                :
     * }
     * </pre>
     *
     * @param htBuilder HandsonTableBuilderクラス
     */
    public abstract void setHtColumns(HandsonTableBuilder htBuilder);

    // -----------------------------------------
    /**
     * エラー時のメッセージ
     */
    protected final String ERRMSG_MST_FORMAT = "マスタ形式で入力してください";
    protected final String ERRMSG_MST_EXIST = "マスタに存在しません";
    protected final String ERRMSG_REQUIRED = "入力してください";
    protected final String ERRMSG_MAX_BYTE_LENGTH = "最大バイト数以下を入力してください";
    protected final String ERRMSG_MIN_BYTE_LENGTH = "最小バイト数以上を入力してください";
    protected final String ERRMSG_TEL = "電話番号形式で入力してください";

    /**
     * マスタ形式に変換
     * <p>
     * id：100、name：営業 → 「100：営業」
     *
     * @param id マスタID
     * @param name マスタ名称
     * @return マスタ形式文字列
     */
    protected String convertMstFormat(Object id, String name) {

        return String.format(HandsonTableBuilder.FORMAT_MASTER, String.valueOf(id), name);
    }

    /**
     * マスタ形式チェック
     * <p>
     * 「id：name」形式で入力されているかチェックする<br>
     * 不正な形式の場合trueを返す。
     *
     * @param str チェック対象文字列
     * @return true: 不正な形式、false: 正しい形式
     */
    protected boolean checkNotMstFormat(String str) {

        if (StringUtil.isEmpty(str.trim())) {
            return false;
        }

        Pattern p = Pattern.compile(HandsonTableBuilder.REGEX_MASTER);

        Matcher m = p.matcher(str);
        if (m.find()) {
            return false;
        }
        return true;
    }

    /**
     * マスタ形式文字列からマスタIDを取得
     * <p>
     * 「ID：名称」形式からID部分を取得し、Stringで返す。
     * <p>
     * ex.<br>
     * 「100：営業」 → 「100」
     *
     * @param str マスタ形式文字列
     * @return 数値ID。変換できない場合は0を返す
     */
    protected String getMasterId(String str) {

        Pattern p = Pattern.compile(HandsonTableBuilder.REGEX_MASTER);

        Matcher m = p.matcher(str);

        if (m.find()){
            return m.group(0)
                    .replace(HandsonTableBuilder.MASTER_SEPARATOR, "");
        }

        return "";
    }

    // -------------------------------------------

    /**
     * 規定した形式のエラーメッセージに変換する。
     * <p>
     * ex.<br>
     * itemName：所属、msg：入力してください → 「　・[所属] 入力してください&lt;br/&gt;」
     *
     * @param itemName 項目名
     * @param msg エラー内容
     * @return
     */
    protected String formatErrorMessage(String itemName, String msg) {

        return String.format("　・[%s] %s<br/>", itemName, msg);
    }

    // -----------------------------------------

    /**
     * データMapをJSON文字列へ変換する
     * @return
     */
    public String getJsonString() {

        JSONObject json = new JSONObject( getJsonMap() );

        return json.toString();
    }

    /**
     * JSON変換用のMapを取得
     * @return
     */
    public Map<String, String> getJsonMap() {

        Map<String, String> map = new LinkedHashMap<String, String>();

        setJsonMap(map);

        return map;
    }

    /**
     * JSON変換したいデータをMapに格納する
     * @param map
     */
    protected abstract void setJsonMap(Map<String, String> map);

    // -----------------------------------------

    /**
     * activeJdbcモデルクラスの値を各プロパティへセット
     * <p>
     * モデルクラスの値をHandsonTableへ表示したい内容に変換して格納する。<br>
     * プロパティの型は文字列にすること。
     * <p>
     * 実装例：
     * <pre>{@code
     * public void setProperties(Model model) {
     *     SimJjStaffMonthly staffMon = (SimJjStaffMonthly) model;
     *
     *     this.staffId = Convert.toString(staffMon.getStaffId());
     *
     *     this.supYm = View.strDateToFormatYM(staffMon.getSupYm());
     *
     *     SimMtBelong b = staffMon.getSimMtBelong();
     *     this.belong
     *         = (b == null)? "": convertMstFormat(b.getBelongId(), b.getBelongName());
     * }
     * </pre>
     *
     * @param entity entityクラス
     */
    public abstract void setProperties(Object entity);

    /**
     * JSON文字列から値を取出し、各プロパティへセット
     * <p>
     * 次の実装例で使用している「JSON_KEY_*」はsetJsonMap関数でも使用するため、
     * 実装クラス内でprivate宣言しておく方が良い。
     * <p>
     * 実装例：
     * <pre>{@code
     * public void setProperties(JSONObject json) {
     *
     *     this.staffId  = Convert.toString(json.get(JSON_KEY_STAFFID));
     *     this.rowNum   = Convert.toString(json.get(JSON_KEY_ROWNUM));
     *     this.supYm    = Convert.toString(json.get(JSON_KEY_SUPYM));
     *     this.belong   = Convert.toString(json.get(JSON_KEY_BELONG));
     *     this.prof     = Convert.toString(json.get(JSON_KEY_PROF));
     *     this.supMonth = Convert.toString(json.get(JSON_KEY_SUPMONTH));
     *     this.attend   = Convert.toString(json.get(JSON_KEY_ATTEND));
     *     this.partTime = Convert.toString(json.get(JSON_KEY_PARTTIME));
     * }
     * </pre>
     *
     * @param json JSON文字列
     */
    public abstract void setProperties(JSONObject json);

    // -----------------------------------------

    /**
     * エラー判定配列をカンマ区切りで取得
     * @return
     */
    public String getErrorData() {

        List<String> list = getStrValidList();
        return String.format("[%s]", StringUtils.join(list.iterator(), ", "));
    }

    /**
     * エラー判定配列を文字列配列に変換して取得
     * @return
     */
    public List<String> getStrValidList() {

        List<String> list = new ArrayList<String>();

        LinkedList<Boolean> validList = new LinkedList<Boolean>();
        this.setValidList(validList);

        for (Boolean b : validList) {
            list.add(String.valueOf(b));
        }

        return list;
    }

    /**
     * HandsonTableのエラー判定配列を取得
     * <p>
     * 実装クラスでは、判定結果の配列を定義する。(表示する列のみ)<br>
     * エラーの列は背景色をピンクに変更して表示される。
     * <p>
     * 実装例：
     * <pre>{@code
     * protected void setValidArray(List<Boolean> vl) {
     *
     *     vl.add(this.validSupYm);
     *     vl.add(this.validBelong);
     *     vl.add(this.validProf);
     *     vl.add(this.validSupMonth);
     *     vl.add(this.validAttend);
     *     vl.add(this.validPartTime);
     * }
     * </pre>
     * @return
     */
    protected abstract void setValidList(List<Boolean> vl);

    // -------------------------------------------

    /**
     * 入力チェックを行い、行番号を付けたエラーメッセージを返す
     * @param row 行数値
     * @return エラーメッセージ
     */
    public String validate(int row) {

        String errMsg = "";

        errMsg = validCore(errMsg);

        if (errMsg.length() == 0) {
            return "";
        }

        return String.valueOf(row)+ "行目<br />"+ errMsg;
    }

    /**
     * 入力チェックを行う
     * <p>
     * 実装クラスにて、各プロパティに対して入力チェックを行い、入力エラーがあればエラーメッセージを追加して返す。<br>
     * エラーメッセージはformatErrorMessage関数を使用して作成する。
     * <p>
     * 実装例：
     * <pre>{@code
     * protected String validCore(String errMsg) {
     *
     *     if (this.staffId.equals("")) {
     *         errMsg += formatErrorMessage("職員番号", "入力してください");
     *     }
     *
     *     return errMsg;
     * }
     * </pre>
     *
     * @param errMsg エラーメッセージ
     * @return 追加後エラーメッセージ
     */
    protected abstract String validCore(String errMsg);

    // -------------------------------------------

    /**
     * 各プロパティの値を保存する
     * <p>
     * 実装例：
     * <pre>{@code
     * public void save() {
     *
     *     Integer staffId = Convert.toInteger(this.staffId);
     *     Integer rowNum = Convert.toInteger(this.rowNum);
     *
     *     SimJjStaffMonthly sm = SimJjStaffMonthly.findByKey(staffId, rowNum);
     *
     *     sm.setSupYm(this.supYm);
     *     sm.setBelongId( getMasterId(this.belong) );
     *     sm.setProfId( getMasterId(this.prof) );
     *     sm.setSupMonthId( getMasterId(this.supMonth) );
     *     sm.setAttendId( getMasterId(this.attend) );
     *     sm.setPartTimeId( getMasterId(this.partTime) );
     *     sm.save();
     * }
     * </pre>
     */
    public abstract void save();
}
