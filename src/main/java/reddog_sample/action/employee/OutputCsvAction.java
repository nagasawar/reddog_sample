package reddog_sample.action.employee;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.javalite.common.Convert;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.entity.Common;
import reddog_sample.entity.Department;
import reddog_sample.entity.Employee;
import reddog_sample.form.employee.IndexForm;
import reddog_sample.service.SF;
import reddog_sample.util.annotation.PostCheck;
import reddog_sample.util.helper.Logic;

// 社員 > CSV出力

public class OutputCsvAction extends AbstractBaseAction {

    @Resource
    @ActionForm
    protected IndexForm indexForm;

    // --------------------------------------------------------
    @PostCheck
    @Execute(validator = false)
    public String index() throws Exception {

        List<String> csvList = new ArrayList<String>();

        // ---------------------------------------------------------------
        // ヘッダー読み込み
        // ---------------------------------------------------------------
        csvList.add( _getHeader() );

        // ---------------------------------------------------------------
        // データを取得
        // ---------------------------------------------------------------
        List<Employee> dataList = SF.employee.findAll();

        // ---------------------------------------------------------------
        // ボディ読み込み
        // ---------------------------------------------------------------
        _setBody(csvList, dataList);

        // ---------------------------------------------------------------
        // CSV出力
        // ---------------------------------------------------------------
        Logic.writeResponseCSV(csvList, "社員情報一覧");

        return null;
    }

    /**
     * CSVヘッダー読み込み
     */
    private String _getHeader() {

        List<String> header = new ArrayList<String>();

        header.add("社員ID");
        header.add("氏名");
        header.add("フリガナ");
        header.add("所属ID");
        header.add("所属名");
        header.add("性別");
        header.add("生年月日");
        header.add("郵便番号");
        header.add("住所1");
        header.add("住所2");
        header.add("住所3");
        header.add("電話番号");
        header.add("備考");

        return Logic.formatCSV(header);
    }

    /**
     * CSVボディ読み込み
     */
    private void _setBody(
            List<String> csvList,
            List<Employee> dataList) {

        for (Employee o : dataList) {
            List<String> csv = new ArrayList<String>();

            csv.add( o.employeeId );
            csv.add( o.fullname );
            csv.add( o.kana );
            csv.add( o.departmentId );
            csv.add( this._getDepartmentName(o) );
            csv.add( this._getGenderName(o) );
            csv.add( this._getBirthday(o) );
            csv.add( o.postCode );
            csv.add( o.address1 );
            csv.add( o.address2 );
            csv.add( o.address3 );
            csv.add( o.tel );
            csv.add( o.note );

            csvList.add( Logic.formatCSV(csv) );
        }

    }

    // ▼ 文字変換 --------------------------------------------------------------
    /**
     * 所属名
     */
    private String _getDepartmentName(Employee o) {

        Department d = SF.department.getOne(o.departmentId);

        return (d == null)? "": d.departmentName;
    }

    /**
     * 性別
     */
    private String _getGenderName(Employee o) {

        Common c = SF.common.getGender(o.gender);

        return (c == null)? "": c.cname;
    }

    /**
     * 生年月日
     */
    private String _getBirthday(Employee o) {

        return Convert.toString(o.birthday);
    }
}
