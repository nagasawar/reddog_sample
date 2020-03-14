package reddog_sample.constants;

/**
 * 帳票周りの定数を定義する
 */
public class ConstReports {

    public static String JASPER_PATH = "WEB-INF/template/reports/jasper/";

    // ------------------------------------

    /**
     * 社員一覧
     */
    public static final String EMPLOYEE_LIST = ConstReports.JASPER_PATH + "employeeList.jasper";

    /**
     * 社員一覧サブレポート
     */
    public static final String EMPLOYEE_LIST_SUB = ConstReports.JASPER_PATH + "employeeList_sub.jasper";

    /**
     * 社員個人情報
     */
    public static final String EMPLOYEE_INFO = ConstReports.JASPER_PATH + "employeeInfo.jasper";
}
