package reddog_sample.action.employee.xlsObj;

import java.util.Date;

import lombok.Data;
import reddog_sample.entity.Common;
import reddog_sample.entity.Department;
import reddog_sample.service.SF;
import reddog_sample.util.helper.View;
import reddog_sample.util.helper.enums.DateFormat;

/**
 * 社員Excel値クラス
 */
@Data
public class XlsEmployee {

    private String employeeId;
    private String fullname;
    private String departmentId;
    private String gender;
    private Date birthday;

    // ▼ 文字変換 --------------------------------------------------------------
    /**
     * 所属名
     */
    public String getDepartmentName() {

        Department d = SF.department.getOne(this.departmentId);

        return (d == null)? "": d.departmentName;
    }

    /**
     * 性別 名称
     */
    public String getGenderName() {

        Common c = SF.common.getGender(this.gender);

        return (c == null)? "": c.cname;
    }

    /**
     * 生年月日 yyyy/MM/dd
     */
    public String getBirthdayFormat() {

        return View.date2Str(this.birthday, DateFormat.YYYYMMDD);
    }
}
