package reddog_sample.form.user;

import java.util.List;

import org.seasar.struts.annotation.Maxbytelength;
import org.seasar.struts.annotation.Required;

import reddog_sample.form.AbstractBaseForm;
import reddog_sample.ignore.entity.RdUserRole;
import reddog_sample.service.SF;
import reddog_sample.util.helper.Logic;
import reddog_sample.util.validator.annotation.HalfNumberOrEn;
import reddog_sample.util.validator.annotation.Selected;

// ユーザ > 新規作成、編集で使用するFormクラス
public class InputForm extends AbstractBaseForm {

    public InputForm() {

        // ユーザ権限リストをセット
        this.rdUserRoles = SF.rdUserRole.findAll();

        // ユーザ権限mapを取得し、ID名称文字列に変換する
        this.rdUserRoleIdNameStr = Logic.map2IdNameString(SF.rdUserRole.getIdNameMap());
    }

    /** ユーザ権限リスト */
    public List<RdUserRole> rdUserRoles;

    /** ユーザ権限 値名称文字列 */
    public String rdUserRoleIdNameStr;


    public String beforeLoginId;

    @Required
    @Maxbytelength(maxbytelength = 250)
    @HalfNumberOrEn
    public String loginId;

    @Required
    @Maxbytelength(maxbytelength = 200)
    @HalfNumberOrEn
    public String password;

    @Required
    @Maxbytelength(maxbytelength = 100)
    public String userName;

    @Selected
    public String userRoleId;

    public void reset() {
        loginId  = "";
        password = "";
        userName = "";
        userRoleId = "";
    }
}
