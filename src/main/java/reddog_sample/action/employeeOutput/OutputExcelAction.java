package reddog_sample.action.employeeOutput;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.seasar.framework.beans.util.Beans;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.action.employeeOutput.xlsObj.XlsEmployeeInfo;
import reddog_sample.constants.ConstExcel;
import reddog_sample.entity.Employee;
import reddog_sample.form.employeeOutput.EmployeeOutputForm;
import reddog_sample.service.SF;
import reddog_sample.util.annotation.PostCheck;
import reddog_sample.util.helper.Logic;

// 社員 > EXCEL出力

public class OutputExcelAction extends AbstractBaseAction {

    @Resource
    @ActionForm
    protected EmployeeOutputForm employeeOutputForm;

    public ServletContext servlet;

    // --------------------------------------------------------
    @PostCheck
    @Execute(validator = false)
    public String index() throws Exception {

        // ---------------------------------------------------------------
        // データを取得し、Excel用のクラスを作成
        // ---------------------------------------------------------------
        XlsEmployeeInfo xlsData = _createXlsData();

        if (xlsData == null) {
            super.addErrorMsg("Excelデータがありません");
            return "/employeeOutput?redirect=true";
        }

        // ---------------------------------------------------------------
        // 値をセット
        // ---------------------------------------------------------------
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("title", "タイトルである");
        params.put("o", xlsData);

        // ---------------------------------------------------------------
        // テンプレートの絶対パス、ファイル名を設定
        // ---------------------------------------------------------------
        String realPath = servlet.getRealPath(ConstExcel.EMPLOYEE_DETAIL_XLS);
        String fileName = "社員情報";

        // ---------------------------------------------------------------
        // Excel出力処理
        // ---------------------------------------------------------------
        Logic.writeResponseExcel(realPath, params, fileName);

        return null;
    }

    // ▽ private ----------------------------------------------------------------------
    /**
     * データを取得し、帳票用のクラスを作成
     */
    private XlsEmployeeInfo _createXlsData() {

        // 社員情報を取得
        Employee employee = SF.employee.getOne(employeeOutputForm.employeeId);

        if (employee == null) {
            return null;
        }

        // Excelクラスを作成
        XlsEmployeeInfo xlsData = new XlsEmployeeInfo();
        Beans.copy(employee, xlsData).execute();

        return xlsData;
    }
}
