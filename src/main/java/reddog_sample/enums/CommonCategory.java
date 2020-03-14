package reddog_sample.enums;

/**
 * CommonsテーブルのCategory列挙型
 */
public enum CommonCategory {
    GENDER("gender"),
//    TYPE2("BBB"),
//    TYPE3("CCC"),
    ;

    private final String text;

    private CommonCategory(final String text) {
        this.text = text;
    }

    public String getString() {
        return this.text;
    }
}