package reddog_sample.util.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

import reddog_sample.util.helper.enums.DateFormat;

/**
 * Viewクラス
 * Action, Jsp, Report, Csvで使用する
 * 表示のために値を変換する共通関数を取りまとめる
 */
public class View {

    /**
     * 郵便番号形式にする
     * 1234567 → 123-4567
     *
     * @param strZip
     * @return 変換できない場合は値をそのまま返す
     */
    public static String formatZip(String strZip) {
        if (strZip == "" || !strZip.matches("^[0-9]{7}$")) {
            return "";
        }
        return strZip.substring(0, 3) + "-" + strZip.substring(3);
    }

    //TODO formatの「YYYYMMDD」以外は未確認
    /**
     * 日付型を指定した日付フォーマットの文字列に変換する。
     *
     * @param d
     * @param format 日付形式
     * @return 指定フォーマットの文字列日付を返す。日付がNullの場合は空文字を返す。
     */
    public static String date2Str(Date d, DateFormat format) {

        if (d == null) return "";

        SimpleDateFormat sf = new SimpleDateFormat(format.getString());
        return sf.format(d);
    }
}
