package reddog_sample.action.department;

import javax.annotation.Resource;

import org.seasar.framework.beans.util.Beans;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.entity.Department;
import reddog_sample.form.department.InputForm;
import reddog_sample.service.SF;
import reddog_sample.util.annotation.AjaxCheck;
import reddog_sample.util.annotation.TokenSave;

// 所属 > 編集表示

public class EditAction extends AbstractBaseAction {

    @Resource
    @ActionForm
    protected InputForm inputForm;

    @AjaxCheck
    @TokenSave
    @Execute(validator = false)
    public String index() throws Exception {

        // ---------------------------------------------------------------
        // データを取得
        // ---------------------------------------------------------------
        Department department = SF.department.getOne(inputForm.ajaxEditorKeyId);
        if (department == null) {
            return forwardBerrorAjax(inputForm, "所属情報が存在しません");
        }

        // ---------------------------------------------------------------
        // Form値セット
        // ---------------------------------------------------------------
        Beans.copy(department, inputForm)
             .execute();

        return "edit.jsp";
    }
}
