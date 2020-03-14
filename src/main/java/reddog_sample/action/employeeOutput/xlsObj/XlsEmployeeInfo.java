package reddog_sample.action.employeeOutput.xlsObj;

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
public class XlsEmployeeInfo {

    private String employeeId;
    private String fullname;
    private String kana;
    private String departmentId;
    private String gender;
    private Date birthday;
    private String postCode;
    private String address1;
    private String address2;
    private String address3;
    private String tel;
    private String note;

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

    /**
     * 郵便番号 123-5678
     */
    public String getPostCodeFormat() {

        return View.formatZip(this.postCode);
    }

    /**
     * 住所1 + 住所2 + 住所3
     */
    public String getAddress() {

        return this.address1 + this.address2 + this.address3;
    }
}
