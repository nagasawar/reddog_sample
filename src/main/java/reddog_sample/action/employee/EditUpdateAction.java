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
import reddog_sample.util.annotation.TokenCheck;

// 社員 > 更新実行
// 社員IDが任意で変更できるため、DELETE INSERTする

public class EditUpdateAction extends AbstractBaseAction {

    @Resource
    @ActionForm
    protected InputForm inputForm;

    @AjaxCheck
    @TokenCheck
    @Execute(validator = true, input="/employee/edit/edit.jsp")
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
        Beans.copy(inputForm, employee)
             .dateConverter("yyyy/MM/dd", "birthday")
             .execute();

        // ---------------------------------------------------------------
        // 更新実行
        // ---------------------------------------------------------------
        SF.employee.update(employee);

        // ---------------------------------------------------------------
        // Ajaxダイアログを閉じさせる
        // ---------------------------------------------------------------
        inputForm.closeAjaxEditor = true;

        return "/employee/edit/edit.jsp";
    }
}
