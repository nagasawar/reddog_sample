package reddog_sample.util.helper.enums;

//TODO 動作未確認
/**
 * 文字エンコードの列挙型
 */
public enum Encode {
    SJIS("sjis"),
    UTF8("utf-8"),
    ;

    private final String text;

    private Encode(final String text) {
        this.text = text;
    }

    public String getString() {
        return this.text;
    }
}