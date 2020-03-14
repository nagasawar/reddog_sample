package reddog_sample.action.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.seasar.framework.beans.util.Beans;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.action.employee.xlsObj.XlsEmployee;
import reddog_sample.constants.ConstExcel;
import reddog_sample.entity.Employee;
import reddog_sample.form.employee.IndexForm;
import reddog_sample.service.SF;
import reddog_sample.util.annotation.PostCheck;
import reddog_sample.util.helper.Logic;
import reddog_sample.util.helper.View;
import reddog_sample.util.helper.enums.DateFormat;

// 社員 > EXCEL出力

public class OutputExcelAction extends AbstractBaseAction {

    @Resource
    @ActionForm
    protected IndexForm indexForm;

    public ServletContext servlet;

    // --------------------------------------------------------
    @PostCheck
    @Execute(validator = false)
    public String index() throws Exception {

        // ---------------------------------------------------------------
        // データを取得し、Excel用のクラスを作成
        // ---------------------------------------------------------------
        List<XlsEmployee> xlsData = _createXlsData();

        // ---------------------------------------------------------------
        // 値をセット
        // ---------------------------------------------------------------
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("title", "タイトルである");
        params.put("nowDate", View.date2Str(new Date(), DateFormat.YYYYMMDD));
        params.put("employeeList", xlsData);

        // ---------------------------------------------------------------
        // テンプレートの絶対パス、ファイル名を設定
        // ---------------------------------------------------------------
        String realPath = servlet.getRealPath(ConstExcel.EMPLOYEE_LIST_XLS);
        String fileName = "社員一覧";

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
    private List<XlsEmployee> _createXlsData() {

        // 社員リストを取得
        List<Employee> employees = SF.employee.findAll();

        // Excel値クラスを作成
        List<XlsEmployee> xlsEmployees = new ArrayList<XlsEmployee>();
        for (Employee o : employees) {
            XlsEmployee r = new XlsEmployee();

            Beans.copy(o, r).execute();
            xlsEmployees.add(r);
        }

        return xlsEmployees;
    }
}
