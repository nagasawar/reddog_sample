package reddog_sample.form.employee;

import java.util.List;

import org.seasar.struts.annotation.Arg;
import org.seasar.struts.annotation.Maxbytelength;
import org.seasar.struts.annotation.Minbytelength;
import org.seasar.struts.annotation.Required;

import reddog_sample.entity.Common;
import reddog_sample.entity.Department;
import reddog_sample.form.AbstractBaseForm;
import reddog_sample.service.SF;
import reddog_sample.util.helper.Logic;
import reddog_sample.util.validator.annotation.DateTypeEx;
import reddog_sample.util.validator.annotation.HalfChar;
import reddog_sample.util.validator.annotation.HalfNumber;
import reddog_sample.util.validator.annotation.Selected;
import reddog_sample.util.validator.annotation.Tel;

// 社員 > 新規作成、編集で使用するFormクラス
public class InputForm extends AbstractBaseForm {

    public InputForm() {

        // 所属リストをセット
        this.departments = SF.department.findAll();

        // 所属mapを取得し、ID名称文字列に変換する
        this.departmentIdNameStr = Logic.map2IdNameString(SF.department.getIdNameMap());

        // 性別リストをセット
        this.genders = SF.common.getGenderList();

        // 性別mapを取得し、ID名称文字列に変換する
        this.genderIdNameStr = Logic.map2IdNameString(SF.common.getGenderIdNameMap());

        // 新規作成URLの場合にフラグを立てる(社員番号のテキストボックス切り替えに使用)
        if (super.request.getServletPath().endsWith("addNew.do") ||
                super.request.getServletPath().endsWith("addNewCreate.do")) {
            this.addNewMode = true;
        }
    }

    /** 所属リスト */
    public List<Department> departments;

    /** 所属 値名称文字列 */
    public String departmentIdNameStr;

    /** 性別リスト */
    public List<Common> genders;

    /** 性別 値名称文字列 */
    public String genderIdNameStr;

    /**
     * 新規作成モード
     * 新規作成画面を判断するためのフラグ
     */
    public boolean addNewMode = false;

    @Required
    @HalfNumber
    @Maxbytelength(maxbytelength = 20)
    public String employeeId;

    @Required
    @Maxbytelength(maxbytelength = 100)
    public String fullName;

    @Required
    @Maxbytelength(maxbytelength = 100)
    @HalfChar
    public String kana;

    public boolean maidenNameFlg;

    @Selected(arg0 = @Arg(key="所属", resource=false))
    public String departmentId;

    public int gender = 1;

    @DateTypeEx
    public String birthday = "";

    @HalfNumber
    @Maxbytelength(maxbytelength = 7)
    @Minbytelength(minbytelength = 7)
    public String postCode;

    @Maxbytelength(maxbytelength = 200)
    public String address1;

    @Maxbytelength(maxbytelength = 200)
    public String address2;

    @Maxbytelength(maxbytelength = 200)
    public String address3;

    @Tel
    public String tel;

    @Maxbytelength(maxbytelength = 4000)
    public String note;

    public void reset() {
        employeeId   = "";
        fullName     = "";
        kana         = "";
        departmentId = "";
        gender       = 1;
        birthday     = "";
        postCode     = "";
        address1     = "";
        address2     = "";
        address3     = "";
        tel          = "";
        note         = "";
    }
}
