package reddog_sample.util.helper.enums;

/**
 * 拡張子の列挙型
 */
public enum Extension {
    XLS  ("xls"),
    XLSX ("xlsx"),
    XLSM ("xlsm"),
    ;

    private final String text;

    private Extension(final String text) {
        this.text = text;
    }

    public String getString() {
        return this.text;
    }

    public static Extension getValue(final String id) {
        Extension[] values = Extension.values();
        for (Extension v : values) {
            if (v.toString().toLowerCase().equals( id.toLowerCase() )) {
                return v;
            }
        }
        return null;
    }
}