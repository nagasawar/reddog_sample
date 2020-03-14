package reddog_sample.action.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.seasar.framework.beans.util.Beans;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.action.employee.rptObj.RptEmployee;
import reddog_sample.action.employee.rptObj.RptEmployeeList;
import reddog_sample.constants.ConstReports;
import reddog_sample.entity.Employee;
import reddog_sample.form.employee.IndexForm;
import reddog_sample.service.SF;
import reddog_sample.util.annotation.PostCheck;
import reddog_sample.util.helper.Logic;
import reddog_sample.util.helper.View;
import reddog_sample.util.helper.enums.DateFormat;

// 社員 > PDF出力

public class OutputPDFAction extends AbstractBaseAction {

    @Resource
    @ActionForm
    protected IndexForm indexForm;

    public ServletContext servlet;

    // --------------------------------------------------------
    @PostCheck
    @Execute(validator = false)
    public String index() throws Exception {

        // ---------------------------------------------------------------
        // データを取得し、帳票用のクラスを作成
        // ---------------------------------------------------------------
        RptEmployeeList rptData = _createRptData();

        // ---------------------------------------------------------------
        // パラメータを作成
        // ---------------------------------------------------------------
        Map<String, Object> params = _getParams();

        // ---------------------------------------------------------------
        // サブレポート情報をパラメータに追加
        // ---------------------------------------------------------------
        _setSubReport(params);

        // ---------------------------------------------------------------
        // PDF出力処理
        // ---------------------------------------------------------------
        String realPath = _getReportRealPath();
        Logic.writeResponsePDF(realPath, rptData, params, "社員情報一覧");

        return null;
    }

    // ▽ private ----------------------------------------------------------------------
    /**
     * データを取得し、帳票用のクラスを作成
     */
    private RptEmployeeList _createRptData() {

        // 社員リストを取得
        List<Employee> employees = SF.employee.findAll();

        // 帳票値クラスを作成
        List<RptEmployee> rptEmployees = new ArrayList<RptEmployee>();
        for (Employee o : employees) {
            RptEmployee r = new RptEmployee();

            Beans.copy(o, r).execute();
            rptEmployees.add(r);
        }

        // 帳票クラスを作成
        RptEmployeeList rptData = new RptEmployeeList();
        rptData.setEmployees(rptEmployees);

        return rptData;
    }

    /**
     * パラメータを作成
     */
    private Map<String, Object> _getParams() {
        Map<String, Object> map = new TreeMap<String, Object>();

        map.put("nowDate", View.date2Str(new Date(), DateFormat.YYYYMMDD));

        return map;
    }

    /**
     * サブレポート情報をパラメータに追加
     */
    private void _setSubReport(Map<String, Object> params) {

        params.put("subReport_employee", servlet.getRealPath(
                ConstReports.EMPLOYEE_LIST_SUB));
    }

    /**
     * jasperファイルパスを取得
     */
    private String _getReportRealPath() {

        return servlet.getRealPath(ConstReports.EMPLOYEE_LIST);
    }
}
