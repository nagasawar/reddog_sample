package reddog_sample.action.employeeBulk.handsonTable;

import java.util.List;
import java.util.Map;

import org.javalite.common.Convert;
import org.json.simple.JSONObject;

import reddog_sample.entity.Common;
import reddog_sample.entity.Department;
import reddog_sample.entity.Employee;
import reddog_sample.service.SF;
import reddog_sample.util.helper.CheckError;
import reddog_sample.util.helper.handsonTable.HandsonTableBuilder;
import reddog_sample.util.helper.handsonTable.HandsonTableColumn;
import reddog_sample.util.helper.handsonTable.HandsonTableData;


/**
 * HandsonTableData 社員一括編集
 */
@SuppressWarnings("boxing")
public class HtEmployee extends HandsonTableData {

    // @columnDefine ----------------------------------------------------
    @Override
    public void setHtColumns(HandsonTableBuilder htBuilder) {

        HandsonTableColumn hc;

        // ---------------------------------------------------------------
        // 社員ID
        // ---------------------------------------------------------------
        hc = new HandsonTableColumn("社員ID", JSON_KEY_EMPLOYEEID);
        hc.setReadOnly(true);
        hc.setColWidth(20);
        htBuilder.addHtColumn(hc);

        // ---------------------------------------------------------------
        // 氏名
        // ---------------------------------------------------------------
        hc = new HandsonTableColumn("氏名", JSON_KEY_FULLNAME);
        hc.setColWidth(30);
        htBuilder.addHtColumn(hc);

        // ---------------------------------------------------------------
        // 所属
        // ---------------------------------------------------------------
        hc = new HandsonTableColumn("所属", JSON_KEY_DEPARTMENT);
        hc.setType(HandsonTableColumn.TYPE_DROPDOWN);
        hc.setAllowInvalid(false);
        hc.setStrict(true);

        hc.addSource("", "");
        for (Department o : SF.department.findAll()) {
            hc.addSource(o.departmentId, o.departmentName);
        }

        htBuilder.addHtColumn(hc);

        // ---------------------------------------------------------------
        // 性別
        // ---------------------------------------------------------------
        hc = new HandsonTableColumn("性別", JSON_KEY_GENDER);
        hc.setType(HandsonTableColumn.TYPE_DROPDOWN);
        hc.setAllowInvalid(false);
        hc.setStrict(true);
        hc.setColWidth(15);

        hc.addSource("", "");
        List<Common> genderList = SF.common.getGenderList();
        for (Common o : genderList) {
            hc.addSource(o.value, o.cname);
        }

        htBuilder.addHtColumn(hc);

        // ---------------------------------------------------------------
        // 電話番号
        // ---------------------------------------------------------------
        hc = new HandsonTableColumn("電話番号", JSON_KEY_TEL);
        htBuilder.addHtColumn(hc);
    }

    // @jsonkey ---------------------------------------------------------
    private final String JSON_KEY_EMPLOYEEID = "employeeId";
    private final String JSON_KEY_FULLNAME   = "fullName";
    private final String JSON_KEY_DEPARTMENT = "department";
    private final String JSON_KEY_GENDER     = "gender";
    private final String JSON_KEY_TEL        = "tel";

    // @properties ------------------------------------------------------
    private String employeeId;
    private String fullName;
    private String department;
    private String gender;
    private String tel;

    // @valid -----------------------------------------------------------
    private boolean validEmployeeId = false;
    private boolean validFullName   = false;
    private boolean validDepartment = false;
    private boolean validGender     = false;
    private boolean validTel        = false;

    // @set methods -----------------------------------------------------
    @Override
    public void setProperties(Object entity) {
        Employee employee = (Employee) entity;

        this.employeeId = employee.employeeId;

        this.fullName = employee.fullname;

        Department d = SF.department.getOne(employee.departmentId);
        this.department
            = (d == null)? "": convertMstFormat(d.departmentId, d.departmentName);

        Common c = SF.common.getGender(employee.gender);
        this.gender
            = (c == null)? "": convertMstFormat(c.value, c.cname);

        this.tel = employee.tel;
    }

    @Override
    public void setProperties(JSONObject json) {

        this.employeeId = Convert.toString(json.get(JSON_KEY_EMPLOYEEID));
        this.department = Convert.toString(json.get(JSON_KEY_DEPARTMENT));
        this.fullName   = Convert.toString(json.get(JSON_KEY_FULLNAME));
        this.gender     = Convert.toString(json.get(JSON_KEY_GENDER));
        this.tel        = Convert.toString(json.get(JSON_KEY_TEL));
    }

    @Override
    protected void setJsonMap(Map<String, String> map) {

        map.put(JSON_KEY_EMPLOYEEID, this.employeeId);
        map.put(JSON_KEY_FULLNAME,   this.fullName);
        map.put(JSON_KEY_DEPARTMENT, this.department);
        map.put(JSON_KEY_GENDER,     this.gender);
        map.put(JSON_KEY_TEL,        this.tel);
    }

    @Override
    protected void setValidList(List<Boolean> vl) {

        vl.add(this.validEmployeeId);
        vl.add(this.validFullName);
        vl.add(this.validDepartment);
        vl.add(this.validGender);
        vl.add(this.validTel);
    }

    // @save -----------------------------------------------------------
    @Override
    public void save() {

        Employee employee = SF.employee.getOne(this.employeeId);

        employee.fullname     = this.fullName;
        employee.departmentId = getMasterId(this.department);
        employee.gender       = getMasterId(this.gender);
        employee.tel          = this.tel;

        SF.employee.update(employee);
    }

    // @validate ------------------------------------------------------
    @Override
    protected String validCore(String errMsg) {

        errMsg += _validFullName();

        errMsg += _validDepartment();

        errMsg += _validGender();

        errMsg += _validTel();

        return errMsg;
    }

    /**
     * 氏名チェック
     */
    private String _validFullName() {

        String itemName = "氏名";

        // 必須
        if (CheckError.requied(this.fullName)) {
            this.validFullName = true;
            return formatErrorMessage(itemName, ERRMSG_REQUIRED);
        }

        // 最大バイト数
        if (CheckError.maxbytelength(this.fullName, 100)) {
            this.validFullName = true;
            return formatErrorMessage(itemName, ERRMSG_MAX_BYTE_LENGTH);
        }

        return "";
    }

    /**
     * 所属チェック
     */
    private String _validDepartment() {

        String itemName = "所属";
        String departmentId = getMasterId(this.department);

        // マスタ形式
        if (checkNotMstFormat(this.department)) {
            this.validDepartment = true;
            return formatErrorMessage(itemName, ERRMSG_MST_FORMAT);
        }

        // 必須
        if (CheckError.requied(this.department)) {
            this.validDepartment = true;
            return formatErrorMessage(itemName, ERRMSG_REQUIRED);
        }

        // マスタ存在
        if (!SF.department.exist(departmentId)) {
            this.validDepartment = true;
            return formatErrorMessage(itemName, ERRMSG_MST_EXIST);
        }

        return "";
    }

    /**
     * 性別チェック
     */
    private String _validGender() {

        String itemName = "性別";
        String value = getMasterId(this.gender);

        // マスタ形式
        if (checkNotMstFormat(this.gender)) {
            this.validGender = true;
            return formatErrorMessage(itemName, ERRMSG_MST_FORMAT);
        }

        // 必須
        if (CheckError.requied(this.gender)) {
            this.validGender = true;
            return formatErrorMessage(itemName, ERRMSG_REQUIRED);
        }

        // マスタ存在
        if (SF.common.getGender(value) == null) {
            this.validGender = true;
            return formatErrorMessage(itemName, ERRMSG_MST_EXIST);
        }

        return "";
    }

    /**
     * 電話番号チェック
     */
    private String _validTel() {

        String itemName = "電話番号";

        // 必須
        if (CheckError.tel(this.tel)) {
            this.validTel = true;
            return formatErrorMessage(itemName, ERRMSG_TEL);
        }

        return "";
    }
}
