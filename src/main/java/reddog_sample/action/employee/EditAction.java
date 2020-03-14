package reddog_sample.action.employee;

import javax.annotation.Resource;

import org.seasar.framework.beans.util.Beans;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.entity.Employee;
import reddog_sample.form.employee.InputForm;
import reddog_sample.service.SF;
import reddog_sample.util.annotation.AjaxCheck;
import reddog_sample.util.annotation.TokenSave;

// 社員 > 編集表示

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
        Employee employee = SF.employee.getOne(inputForm.ajaxEditorKeyId);
        if (employee == null) {
            return forwardBerrorAjax(inputForm, "社員情報が存在しません");
        }

        // ---------------------------------------------------------------
        // Form値セット
        // ---------------------------------------------------------------
        Beans.copy(employee, inputForm)
             .execute();

        return "edit.jsp";
    }
}
