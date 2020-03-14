package reddog_sample.action.employeeOutput;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.seasar.framework.beans.util.Beans;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.action.employeeOutput.rptObj.RptEmployeeInfo;
import reddog_sample.constants.ConstReports;
import reddog_sample.entity.Employee;
import reddog_sample.form.employeeOutput.EmployeeOutputForm;
import reddog_sample.service.SF;
import reddog_sample.util.annotation.PostCheck;
import reddog_sample.util.helper.Logic;
import reddog_sample.util.helper.View;
import reddog_sample.util.helper.enums.DateFormat;

// 社員 > PDF出力

public class OutputPDFAction extends AbstractBaseAction {

    @Resource
    @ActionForm
    protected EmployeeOutputForm employeeOutputForm;

    public ServletContext servlet;

    // --------------------------------------------------------
    @PostCheck
    @Execute(validator = false)
    public String index() throws Exception {

        // ---------------------------------------------------------------
        // データを取得し、帳票用のクラスを作成
        // ---------------------------------------------------------------
        RptEmployeeInfo rptData = _createRptData();

        if (rptData == null) {
            return super.forwardBerror(employeeOutputForm, "PDFデータがありません");
        }

        // ---------------------------------------------------------------
        // パラメータを作成
        // ---------------------------------------------------------------
        Map<String, Object> params = _getParams();

        // ---------------------------------------------------------------
        // PDF出力処理
        // ---------------------------------------------------------------
        String realPath = _getReportRealPath();
        Logic.writeResponsePDF(realPath, rptData, params, "社員情報");

        return null;
    }

    // ▽ private ----------------------------------------------------------------------
    /**
     * データを取得し、帳票用のクラスを作成
     */
    private RptEmployeeInfo _createRptData() {

        // 社員情報を取得
        Employee employee = SF.employee.getOne(employeeOutputForm.employeeId);

        if (employee == null) {
            return null;
        }

        // 帳票クラスを作成
        RptEmployeeInfo rptData = new RptEmployeeInfo();
        Beans.copy(employee, rptData).execute();

        return rptData;
    }

    /**
     * パラメータ項目を作成
     */
    private Map<String, Object> _getParams() {
        Map<String, Object> map = new TreeMap<String, Object>();

        map.put("nowDate", View.date2Str(new Date(), DateFormat.YYYYMMDD));

        return map;
    }

    /**
     * jasperファイルパスを取得
     */
    private String _getReportRealPath() {

        return servlet.getRealPath(ConstReports.EMPLOYEE_INFO);
    }
}
