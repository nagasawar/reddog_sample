package reddog_sample.action.employee;

import javax.annotation.Resource;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.seasar.framework.beans.util.Beans;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

import reddog_sample.action.AbstractBaseAction;
import reddog_sample.entity.Employee;
import reddog_sample.form.employee.InputForm;
import reddog_sample.service.SF;
import reddog_sample.util.annotation.AjaxCheck;
import reddog_sample.util.annotation.TokenCheck;

// 社員 > 新規作成実行

public class AddNewCreateAction extends AbstractBaseAction {

    @Resource
    @ActionForm
    protected InputForm inputForm;

    @AjaxCheck
    @TokenCheck
    @Execute(validator = true, validate = "validate", input="/employee/addNew/addNew.jsp")
    public String index() throws Exception {

        // ---------------------------------------------------------------
        // 空のデータ作成
        // ---------------------------------------------------------------
        Employee employee = new Employee();

        // ---------------------------------------------------------------
        // Form値セット
        // ---------------------------------------------------------------
        Beans.copy(inputForm, employee)
             .dateConverter("yyyy/MM/dd", "birthday")
             .execute();

        // ---------------------------------------------------------------
        // 作成実行
        // ---------------------------------------------------------------
        SF.employee.insert(employee);

        // ---------------------------------------------------------------
        // Ajaxダイアログを閉じさせる
        // ---------------------------------------------------------------
        inputForm.closeAjaxEditor = true;

        // ---------------------------------------------------------------
        // Ajaxダイアログに新規のキーをセット
        // ---------------------------------------------------------------
        inputForm.ajaxEditorKeyId = employee.employeeId;

        return "/employee/addNew/addNew.jsp";
    }

    public ActionMessages validate() {
        ActionMessages errors = new ActionMessages();

        // 社員ID 一意チェック
        if (SF.employee.exist(inputForm.employeeId)) {
            errors.add("employeeId", new ActionMessage("この社員IDはすでに存在します", false));
        }

        return errors;
    }
}
