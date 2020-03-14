package reddog_sample.util.helper.enums;

/**
 * 日付フォーマットの列挙型
 */
public enum DateFormat {
    YYYYMMDD          ("yyyy/MM/dd"),
    YYYYMMDDHHMMSS    ("yyyy/MM/dd HH:mm.ss"),
    JP_YYYYMMDD       ("yyyy年MM月dd日"),
    JP_YYYYMMDDHHMMSS ("yyyy年MM月dd日HH時mm分ss秒");
    ;

    private final String text;

    private DateFormat(final String text) {
        this.text = text;
    }

    public String getString() {
        return this.text;
    }
}