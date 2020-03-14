package reddog_sample.form.department;

import org.seasar.struts.annotation.Maxbytelength;
import org.seasar.struts.annotation.Required;

import reddog_sample.form.AbstractBaseForm;
import reddog_sample.util.validator.annotation.HalfNumber;

// 所属 > 新規作成、編集で使用するFormクラス
public class InputForm extends AbstractBaseForm {

    public InputForm() {

        // 新規作成URLの場合にフラグを立てる(所属IDのテキストボックス切り替えに使用)
        if (super.request.getServletPath().endsWith("addNew.do") ||
                super.request.getServletPath().endsWith("addNewCreate.do")) {
            this.addNewMode = true;
        }
    }

    /**
     * 新規作成モード
     * 新規作成画面を判断するためのフラグ
     */
    public boolean addNewMode = false;

    @Required
    @HalfNumber
    @Maxbytelength(maxbytelength = 20)
    public String departmentId;

    @Required
    @Maxbytelength(maxbytelength = 100)
    public String departmentName;

    public void reset() {
        departmentId = "";
        departmentName = "";
    }
}
