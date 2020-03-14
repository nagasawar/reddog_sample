package reddog_sample.util.helper;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;

import org.seasar.framework.util.StringUtil;

/**
 * <pre>
 * CheckErrorヘルパークラス
 *   入力チェックの判定を行う。
 *   エラーの場合はtrue、エラーではない場合はfalseになる。
 * </pre>
 */
public class CheckError {

    /**
     * 必須チェックでエラーかどうかを判定する
     * @param value 検証値
     * @return true:エラーあり、false:なし
     */
    public static boolean requied(String value) {
        return StringUtil.isEmpty(value.trim());
    }

    /**
     * 半角文字チェックでエラーかどうかを判定する
     * @param value 検証値
     * @return true:エラーあり、false:なし
     * @throws UnsupportedEncodingException
     */
    public static boolean halfChar(String value) {

        // 空文字はOK
        if (StringUtil.isEmpty(value.trim())) {
            return false;
        }

        // 半角文字でない場合はエラー
        //   半角ｶﾅ 0-9 a-z A-Z ( ) . / - 半角ｽﾍﾟｰｽ
        if (!value.matches("^[\uFF65-\uFF9F0-9a-zA-Z \\(\\)\\.\\/\\-]+$")) {
            return true;
        }

        return false;
    }

    /**
     * 半角数字チェックでエラーかどうかを判定する
     * @param value 検証値
     * @return true:エラーあり、false:なし
     */
    public static boolean halfNumber(String value) {

        // 空文字はOK
        if (StringUtil.isEmpty(value.trim())) {
            return false;
        }

        // 半角数字でない場合はエラー
        if (!value.matches("^[0-9]*$")) {
            return true;
        }

        return false;
    }

    /**
     * 半角英数字チェックでエラーかどうかを判定する
     * @param value 検証値
     * @return true:エラーあり、false:なし
     */
    public static boolean halfNumberOrEn(String value) {

        // 空文字はOK
        if (StringUtil.isEmpty(value.trim())) {
            return false;
        }

        // 半角英数字でない場合はエラー
        if (!value.matches("^[0-9a-zA-Z]*$")) {
            return true;
        }

        return false;
    }

    /**
     * 電話番号形式チェックでエラーかどうかを判定する
     * @param value 検証値
     * @return true:エラーあり、false:なし
     */
    public static boolean tel(String value) {

        // 空文字はOK
        if (StringUtil.isEmpty(value.trim())) {
            return false;
        }

        // 電話番号形式でない場合はエラー
        if (!value.matches("^0\\d{1,4}-\\d{1,4}-\\d{4}$")) {
            return true;
        }

        return false;
    }

    /**
     * 日付形式チェックでエラーかどうかを判定する
     *   ・「yyyy/MM/dd」形式
     *       OK例） 2017/01/02, 2017/1/2, 2017/01/2, 2017/1/02
     *   ・1753/01/01 ～ 9999/12/31の間
     *   ・日付の妥当性
     * @param value 検証値
     * @return true:エラーあり、false:なし
     */
    public static boolean date(String value) {

        // 空文字はOK
        if (StringUtil.isEmpty(value.trim())) {
            return false;
        }

        // 形式チェック
        if (!value.matches("^[0-9]{4}(-|/)[0-9]{1,2}(-|/)[0-9]{1,2}$")) {
            return true;
        }

        // 年範囲チェック
        // SQL SEVERで扱える範囲は1753/01/01 ～ 9999/12/31年の間のため、
        // 年が範囲内であるかチェックする。
        // 月日については、次の日付妥当性チェックで行う
        String[] dateList;
        if (value.indexOf("-") != -1) {
            dateList = value.split("-");
        } else {
            dateList = value.split("/");
        }
        int iYear = Integer.parseInt(dateList[0]);
        if (iYear < 1753 || 9999 < iYear) {
            return true;
        }

        // 日付妥当性チェック
        value = value.replace('-', '/');
        DateFormat format = DateFormat.getDateInstance();
        format.setLenient(false);
        try {
            format.parse(value);

        } catch (Exception e) {
            return true;
        }

        return false;
    }

    /**
     * 最大バイト数チェックでエラーかどうかを判定する
     * @param value 検証値
     * @param max 最大バイト数
     * @return true:エラーあり、false:なし
     */
    public static boolean maxbytelength(String value, int max) {

        int byteLen = Logic.getByte(value);
        return (byteLen > max);
    }

    /**
     * 最小バイト数チェックでエラーかどうかを判定する
     * @param value 検証値
     * @param min 最小バイト数
     * @return true:エラーあり、false:なし
     */
    public static boolean minbytelength(String value, int min) {

        int byteLen = Logic.getByte(value);
        return (byteLen < min);
    }
}
