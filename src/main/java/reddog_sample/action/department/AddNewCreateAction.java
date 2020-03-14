package reddog_sample.action.department;

import javax.annotation.Resource;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.framework.beans.util.Beans;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.entity.Department;
import reddog_sample.form.department.InputForm;
import reddog_sample.service.SF;
import reddog_sample.util.annotation.AjaxCheck;
import reddog_sample.util.annotation.TokenCheck;

// 所属 > 新規作成実行

public class AddNewCreateAction extends AbstractBaseAction {

    @Resource
    @ActionForm
    protected InputForm inputForm;

    @AjaxCheck
    @TokenCheck
    @Execute(validator = true, validate = "validate", input="/department/addNew/addNew.jsp")
    public String index() throws Exception {

        // ---------------------------------------------------------------
        // 空データ作成
        // ---------------------------------------------------------------
        Department department = new Department();

        // ---------------------------------------------------------------
        // Form値セット
        // ---------------------------------------------------------------
        Beans.copy(inputForm, department)
             .execute();

        // ---------------------------------------------------------------
        // 作成実行
        // ---------------------------------------------------------------
        SF.department.insert(department);

        // ---------------------------------------------------------------
        // Ajaxダイアログを閉じさせる
        // ---------------------------------------------------------------
        inputForm.closeAjaxEditor = true;

        // ---------------------------------------------------------------
        // Ajaxダイアログに新規のキーをセット
        // ---------------------------------------------------------------
        inputForm.ajaxEditorKeyId = department.departmentId;

        return "/department/addNew/addNew.jsp";
    }

    public ActionMessages validate() {
        ActionMessages errors = new ActionMessages();

        // 所属ID 一意チェック
        if (SF.department.exist(inputForm.departmentId)) {
            errors.add("departmentId", new ActionMessage("この所属IDはすでに存在します", false));
        }

        return errors;
    }
}
