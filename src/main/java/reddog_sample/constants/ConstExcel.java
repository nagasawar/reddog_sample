package reddog_sample.constants;

/**
 * エクセル周りの定数を定義する
 */
public class ConstExcel {

    public static String EXCEL_PATH = "WEB-INF/template/excel/";

    // ------------------------------------

    /**
     * 社員一覧
     */
    public static final String EMPLOYEE_LIST_XLS = ConstExcel.EXCEL_PATH + "employeeList.xls";
    public static final String EMPLOYEE_LIST_XLSX = ConstExcel.EXCEL_PATH + "employeeList.xlsx";
    public static final String EMPLOYEE_LIST_XLSM = ConstExcel.EXCEL_PATH + "employeeList.xlsm";

    /**
     * 社員個人情報
     */
    public static final String EMPLOYEE_DETAIL_XLS = ConstExcel.EXCEL_PATH + "employee.xls";
    public static final String EMPLOYEE_DETAIL_XLSX = ConstExcel.EXCEL_PATH + "employee.xlsx";
    public static final String EMPLOYEE_DETAIL_XLSM = ConstExcel.EXCEL_PATH + "employee.xlsm";
}
